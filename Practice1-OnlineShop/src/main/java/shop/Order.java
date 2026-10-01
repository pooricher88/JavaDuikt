package shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Замовлення, сформоване з товарів у кошику.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private int id;                    // Номер замовлення
    private List<Product> products;    // Товари в замовленні
    private double totalPrice;         // Загальна сума
    private LocalDateTime createdAt;   // Дата і час створення

    // Створення замовлення з поточного вмісту кошика
    public Order(int id, Cart cart) {
        this.id = id;
        this.products = new ArrayList<>(cart.getProducts()); // копія, щоб не залежати від кошика
        this.totalPrice = cart.getTotalPrice();
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append("Замовлення №").append(id)
          .append(" від ").append(createdAt.format(fmt)).append('\n');
        for (Product p : products) {
            sb.append("  - ").append(p.getName())
              .append(" — ").append(String.format("%.2f", p.getPrice())).append(" грн\n");
        }
        sb.append("  Разом: ").append(String.format("%.2f", totalPrice)).append(" грн");
        return sb.toString();
    }
}
