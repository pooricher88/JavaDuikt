package shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Товар інтернет-магазину.
 * Конструктори, гетери та сетери генерує Lombok.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private int id;             // Унікальний ідентифікатор товару
    private String name;        // Назва товару
    private double price;       // Ціна товару
    private String description; // Опис товару
    private Category category;  // Категорія товару

    @Override
    public String toString() {
        return "Товар{" +
                "id=" + id +
                ", назва='" + name + '\'' +
                ", ціна=" + String.format("%.2f", price) +
                ", опис='" + description + '\'' +
                ", категорія='" + (category != null ? category.getName() : "—") + '\'' +
                '}';
    }
}
