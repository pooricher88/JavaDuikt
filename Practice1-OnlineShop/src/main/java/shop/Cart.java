package shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Кошик користувача: зберігає товари, додані до кошика.
 */
@Getter
@Setter
@AllArgsConstructor
public class Cart {
    private List<Product> products; // Список товарів у кошику

    // Конструктор без параметрів — створює порожній кошик
    public Cart() {
        this.products = new ArrayList<>();
    }

    // Додавання товару до кошика
    public void addProduct(Product product) {
        products.add(product);
    }

    // Видалення товару з кошика
    public boolean removeProduct(Product product) {
        return products.remove(product);
    }

    // Загальна вартість товарів у кошику
    public double getTotalPrice() {
        double total = 0;
        for (Product p : products) {
            total += p.getPrice();
        }
        return total;
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    // Очищення кошика (після оформлення замовлення)
    public void clear() {
        products.clear();
    }

    @Override
    public String toString() {
        if (products.isEmpty()) {
            return "Кошик порожній.";
        }
        StringBuilder sb = new StringBuilder("Товари в кошику:\n");
        for (int i = 0; i < products.size(); i++) {
            sb.append("  ").append(i + 1).append(". ").append(products.get(i)).append('\n');
        }
        sb.append("Загальна сума: ").append(String.format("%.2f", getTotalPrice())).append(" грн");
        return sb.toString();
    }
}
