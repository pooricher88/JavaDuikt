package shop;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Головний клас: консольний інтерфейс інтернет-магазину.
 */
public class Main {

    private static final List<Product> catalog = new ArrayList<>();
    private static final List<Order> orders = new ArrayList<>();
    private static final Cart cart = new Cart();
    private static int nextOrderId = 1;

    public static void main(String[] args) {
        // Коректне відображення кирилиці в консолі
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        initCatalog();
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt(scanner, "Ваш вибір: ");

            switch (choice) {
                case 1 -> showCatalog();
                case 2 -> addToCart(scanner);
                case 3 -> System.out.println(cart);
                case 4 -> removeFromCart(scanner);
                case 5 -> makeOrder();
                case 6 -> showOrders();
                case 0 -> {
                    running = false;
                    System.out.println("Дякуємо за покупки! До побачення.");
                }
                default -> System.out.println("Невірний пункт меню. Спробуйте ще раз.");
            }
        }
        scanner.close();
    }

    // Початкове наповнення каталогу
    private static void initCatalog() {
        Category electronics = new Category(1, "Електроніка");
        Category smartphones = new Category(2, "Смартфони");
        Category accessories = new Category(3, "Аксесуари");

        catalog.add(new Product(1, "Ноутбук", 19999.99,
                "Високопродуктивний ноутбук для роботи та ігор", electronics));
        catalog.add(new Product(2, "Смартфон", 12999.50,
                "Смартфон з великим екраном та високою автономністю", smartphones));
        catalog.add(new Product(3, "Навушники", 2499.00,
                "Бездротові навушники з шумозаглушенням", accessories));
        catalog.add(new Product(4, "Монітор", 7499.00,
                "27-дюймовий IPS монітор 144 Гц", electronics));
        catalog.add(new Product(5, "Чохол для смартфона", 399.00,
                "Силіконовий чохол", accessories));
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== ІНТЕРНЕТ-МАГАЗИН =====");
        System.out.println("1. Переглянути каталог товарів");
        System.out.println("2. Додати товар до кошика");
        System.out.println("3. Переглянути кошик");
        System.out.println("4. Видалити товар з кошика");
        System.out.println("5. Оформити замовлення");
        System.out.println("6. Мої замовлення");
        System.out.println("0. Вихід");
    }

    private static void showCatalog() {
        System.out.println("Каталог товарів:");
        for (Product p : catalog) {
            System.out.println("  " + p);
        }
    }

    private static void addToCart(Scanner scanner) {
        showCatalog();
        int id = readInt(scanner, "Введіть ID товару: ");
        Product product = findById(id);
        if (product == null) {
            System.out.println("Товар з ID " + id + " не знайдено.");
        } else {
            cart.addProduct(product);
            System.out.println("Товар \"" + product.getName() + "\" додано до кошика.");
        }
    }

    private static void removeFromCart(Scanner scanner) {
        if (cart.isEmpty()) {
            System.out.println("Кошик порожній.");
            return;
        }
        System.out.println(cart);
        int id = readInt(scanner, "Введіть ID товару для видалення: ");
        Product product = findById(id);
        if (product != null && cart.removeProduct(product)) {
            System.out.println("Товар \"" + product.getName() + "\" видалено з кошика.");
        } else {
            System.out.println("Такого товару немає в кошику.");
        }
    }

    private static void makeOrder() {
        if (cart.isEmpty()) {
            System.out.println("Неможливо оформити замовлення: кошик порожній.");
            return;
        }
        Order order = new Order(nextOrderId++, cart);
        orders.add(order);
        cart.clear();
        System.out.println("Замовлення оформлено!");
        System.out.println(order);
    }

    private static void showOrders() {
        if (orders.isEmpty()) {
            System.out.println("Замовлень ще немає.");
            return;
        }
        for (Order o : orders) {
            System.out.println(o);
        }
    }

    private static Product findById(int id) {
        for (Product p : catalog) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    // Безпечне зчитування цілого числа
    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Введіть ціле число.");
            }
        }
    }
}
