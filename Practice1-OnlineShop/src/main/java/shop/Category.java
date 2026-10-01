package shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Категорія товару (наприклад, "Електроніка", "Одяг").
 * Конструктори, гетери та сетери генерує Lombok.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private int id;       // Унікальний ідентифікатор категорії
    private String name;  // Назва категорії

    @Override
    public String toString() {
        return "Категорія{" +
                "id=" + id +
                ", назва='" + name + '\'' +
                '}';
    }
}
