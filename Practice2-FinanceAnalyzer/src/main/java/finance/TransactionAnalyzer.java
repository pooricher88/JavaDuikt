package finance;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Аналіз транзакцій.
 * Абстрактний клас зі статичними методами: список транзакцій передається параметром,
 * тому не потрібно створювати екземпляр аналізатора.
 */
public abstract class TransactionAnalyzer {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter MONTH_YEAR_FORMATTER = DateTimeFormatter.ofPattern("MM-yyyy");

    /** Задача 2: загальний баланс усіх транзакцій. */
    public static double calculateTotalBalance(List<Transaction> transactions) {
        double balance = 0;
        for (Transaction transaction : transactions) {
            balance += transaction.getAmount();
        }
        return balance;
    }

    /** Задача 3: кількість транзакцій за місяць у форматі "MM-yyyy". */
    public static int countTransactionsByMonth(List<Transaction> transactions, String monthYear) {
        int count = 0;
        for (Transaction transaction : transactions) {
            try {
                LocalDate date = LocalDate.parse(transaction.getDate(), DATE_FORMATTER);
                if (date.format(MONTH_YEAR_FORMATTER).equals(monthYear)) {
                    count++;
                }
            } catch (DateTimeParseException e) {
                // некоректна дата — транзакцію пропускаємо
            }
        }
        return count;
    }

    /** Етап 4: N найбільших витрат (від'ємні суми, від найбільшої за модулем). */
    public static List<Transaction> findTopExpenses(List<Transaction> transactions, int limit) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .sorted(Comparator.comparingDouble(Transaction::getAmount))
                .limit(limit)
                .collect(Collectors.toList());
    }

    /** Етап 4: 10 найбільших витрат. */
    public static List<Transaction> findTopExpenses(List<Transaction> transactions) {
        return findTopExpenses(transactions, 10);
    }

    /** Етап 5: сума витрат за категоріями (опис), відсортована за спаданням. */
    public static Map<String, Double> calculateExpensesByCategory(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(Transaction::getDescription,
                        Collectors.summingDouble(t -> -t.getAmount())))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (a, b) -> a, LinkedHashMap::new));
    }
}
