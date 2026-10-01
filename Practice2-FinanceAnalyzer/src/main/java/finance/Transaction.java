package finance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Модель банківської транзакції.
 * Конструктори, гетери, сетери, equals/hashCode та toString генерує Lombok (@Data).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private String date;        // Дата у форматі dd-MM-yyyy
    private double amount;      // Сума (дохід > 0, витрата < 0)
    private String description; // Опис / категорія
}
