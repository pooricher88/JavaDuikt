package orders.processing;

import orders.model.Order;
import orders.model.OrderStatus;
import orders.model.Product;
import orders.processing.internal.DiscountPolicy;
import orders.storage.OrderRepository;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Обробник замовлень для товарів типу T. Завдяки обмеженню {@code T extends Product}
 * обробник не можна створити для типу, що не є товаром (наприклад, OrderProcessor<String> не скомпілюється).
 *
 * @param <T> тип товару
 */
public class OrderProcessor<T extends Product> {

    private final OrderRepository<T> repository;
    private final DiscountPolicy discountPolicy = new DiscountPolicy();

    public OrderProcessor(OrderRepository<T> repository) {
        this.repository = repository;
    }

    /** Перевіряє і зберігає нове замовлення. */
    public void accept(Order<T> order) throws InvalidOrderException {
        validate(order);
        repository.save(order);
    }

    /** Обробляє одне замовлення: використовується як посилання на метод processor::processOrder. */
    public void processOrder(Order<T> order) {
        order.setStatus(OrderStatus.PROCESSING);
        order.setProcessedBy(Thread.currentThread().getName());
        try {
            Thread.sleep(50); // імітація роботи (оплата, пакування)
            order.setStatus(OrderStatus.COMPLETED);
        } catch (InterruptedException e) {
            order.setStatus(OrderStatus.FAILED);
            Thread.currentThread().interrupt();
        }
    }

    /** Обробляє список замовлень переданою дією (лямбда або посилання на метод). */
    public void processAll(List<Order<T>> orders, Consumer<Order<T>> action) {
        orders.forEach(action);
    }

    public List<Order<T>> filter(Predicate<? super Order<T>> condition) {
        return repository.findBy(condition);
    }

    /** Сума до сплати з урахуванням знижки (знижка рахується внутрішнім класом модуля). */
    public double priceWithDiscount(Order<T> order) {
        return discountPolicy.apply(order.getTotal(), order.getQuantity());
    }

    /** Підсумок для будь-яких замовлень: wildcard з верхньою межею. */
    public static double totalOf(List<? extends Order<? extends Product>> orders) {
        return orders.stream().mapToDouble(Order::getTotal).sum();
    }

    public OrderRepository<T> getRepository() {
        return repository;
    }

    private void validate(Order<T> order) throws InvalidOrderException {
        if (order == null || order.getProduct() == null) {
            throw new InvalidOrderException("Замовлення без товару");
        }
        if (order.getQuantity() <= 0) {
            throw new InvalidOrderException("Кількість має бути більшою за 0 (замовлення #" + order.getId() + ")");
        }
        if (order.getCustomer() == null || order.getCustomer().isBlank()) {
            throw new InvalidOrderException("Не вказано покупця (замовлення #" + order.getId() + ")");
        }
    }
}
