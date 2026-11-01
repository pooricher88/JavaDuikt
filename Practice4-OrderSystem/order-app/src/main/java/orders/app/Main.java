package orders.app;

import orders.concurrency.OrderDispatcher;
import orders.model.Clothing;
import orders.model.Electronics;
import orders.model.Order;
import orders.model.Product;
import orders.processing.InvalidOrderException;
import orders.processing.OrderFilters;
import orders.processing.OrderProcessor;
import orders.storage.OrderRepository;
import orders.storage.ProductTypeMismatchException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        ProductFactory factory = new ProductFactory();

        // Окремі обробники для електроніки та одягу: T обмежений Product
        OrderProcessor<Electronics> electronicsProcessor = new OrderProcessor<>(new OrderRepository<>());
        OrderProcessor<Clothing> clothingProcessor = new OrderProcessor<>(new OrderRepository<>());

        System.out.println("=== 1. Створення замовлень (JavaFaker + Lombok @Builder) ===");
        List<Order<Electronics>> electronicsOrders = createOrders(5, factory::randomElectronics, factory);
        List<Order<Clothing>> clothingOrders = createOrders(5, factory::randomClothing, factory);

        electronicsOrders.forEach(order -> acceptSafely(electronicsProcessor, order));
        clothingOrders.forEach(order -> acceptSafely(clothingProcessor, order));

        System.out.println("\n=== 2. Обробка виключень ===");
        acceptSafely(electronicsProcessor, new Order<>(factory.randomElectronics(), 0, "Іван Петренко"));
        acceptSafely(clothingProcessor, new Order<>(factory.randomClothing(), 2, " "));

        System.out.println("\n=== 3. Багатопотокова обробка (лямбди + посилання на методи) ===");
        try (OrderDispatcher dispatcher = new OrderDispatcher(3)) {
            dispatcher.dispatchAndWait(electronicsProcessor.getRepository().findAll(), electronicsProcessor::processOrder);
            dispatcher.dispatchAndWait(clothingProcessor.getRepository().findAll(), clothingProcessor::processOrder);
            System.out.println("Оброблено: " + dispatcher.getProcessed() + ", з помилками: " + dispatcher.getFailed());
        }

        List<Order<? extends Product>> all = new ArrayList<>();
        all.addAll(electronicsProcessor.getRepository().findAll());
        all.addAll(clothingProcessor.getRepository().findAll());
        all.stream().map(OrderFilters::describe).forEach(System.out::println);

        System.out.println("\n=== 4. Фільтрація лямбдами та посиланнями на методи ===");
        System.out.println("Електроніка: " + all.stream().filter(OrderFilters::isElectronics).count());
        System.out.println("Одяг: " + all.stream().filter(OrderFilters::isClothing).count());
        System.out.println("Дорогі (від " + OrderFilters.EXPENSIVE_LIMIT + " грн):");
        all.stream().filter(OrderFilters::isExpensive).map(OrderFilters::describe).forEach(System.out::println);
        System.out.println("Електроніка Samsung/Apple:");
        electronicsProcessor.filter(o -> Stream.of("Samsung", "Apple").anyMatch(o.getProduct().getBrand()::equals))
                .forEach(o -> System.out.println("  " + o.getProduct().getName() + " — " + o.getCustomer()));
        System.out.printf("Загальна сума всіх замовлень: %.2f грн%n", OrderProcessor.totalOf(all));
        Order<Clothing> firstClothing = clothingOrders.stream()
                .max(Comparator.comparingInt(Order::getQuantity)).orElseThrow();
        System.out.printf("Сума замовлення #%d зі знижкою: %.2f грн (без знижки %.2f)%n",
                firstClothing.getId(), clothingProcessor.priceWithDiscount(firstClothing), firstClothing.getTotal());

        System.out.println("\n=== 5. Виключення при роботі з узагальненими типами ===");
        OrderRepository<Product> mixed = new OrderRepository<>();
        Order<Product> phoneOrder = new Order<>(factory.randomElectronics(), 1, factory.randomCustomer());
        mixed.save(phoneOrder);
        try {
            Electronics ok = mixed.findProduct(phoneOrder.getId(), Electronics.class);
            System.out.println("Знайдено електроніку: " + ok.getName());
            Clothing wrong = mixed.findProduct(phoneOrder.getId(), Clothing.class);
            System.out.println("Цей рядок не виконається: " + wrong);
        } catch (ProductTypeMismatchException e) {
            System.out.println("ProductTypeMismatchException: " + e.getMessage());
        }
    }

    private static <T extends Product> List<Order<T>> createOrders(int count, Supplier<T> productSupplier,
                                                                   ProductFactory factory) {
        List<Order<T>> orders = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            orders.add(new Order<>(productSupplier.get(), factory.randomQuantity(), factory.randomCustomer()));
        }
        orders.forEach(o -> System.out.println("  створено " + OrderFilters.describe(o)));
        return orders;
    }

    private static <T extends Product> void acceptSafely(OrderProcessor<T> processor, Order<T> order) {
        try {
            processor.accept(order);
        } catch (InvalidOrderException e) {
            System.out.println("InvalidOrderException: " + e.getMessage());
        }
    }
}
