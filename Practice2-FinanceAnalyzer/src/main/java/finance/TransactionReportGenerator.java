package finance;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Виведення звітів у консоль.
 * Абстрактний клас зі статичними методами — екземпляр не потрібен.
 */
public abstract class TransactionReportGenerator {

    public static void printBalanceReport(double totalBalance) {
        System.out.printf("Загальний баланс: %.2f%n", totalBalance);
    }

    public static void printTransactionsCountByMonth(String monthYear, int count) {
        System.out.println("Кількість транзакцій за " + monthYear + ": " + count);
    }

    public static void printTopExpenses(List<Transaction> expenses) {
        System.out.println("Топ-" + expenses.size() + " найбільших витрат:");
        for (int i = 0; i < expenses.size(); i++) {
            Transaction t = expenses.get(i);
            System.out.printf("  %2d. %s  %10.2f  %s%n", i + 1, t.getDate(), t.getAmount(), t.getDescription());
        }
    }

    public static void printExpensesByCategory(Map<String, Double> expensesByCategory) {
        System.out.println("Витрати за категоріями (від найбільших):");
        expensesByCategory.forEach((category, sum) ->
                System.out.printf("  %-25s %10.2f%n", category, sum));
    }

    /** Самостійна робота: найбільша та найменша витрати за період. */
    public static void printMinMaxExpenses(String from, String to,
                                           Optional<Transaction> largest, Optional<Transaction> smallest) {
        System.out.println("Витрати за період " + from + " — " + to + ":");
        System.out.println("  Найбільша: " + largest.map(TransactionReportGenerator::format).orElse("немає витрат"));
        System.out.println("  Найменша:  " + smallest.map(TransactionReportGenerator::format).orElse("немає витрат"));
    }

    /**
     * Самостійна робота: текстовий звіт-діаграма.
     * Кожен символ '*' відповідає сумі unit (наприклад, 1000 грн); залишок округлюється вгору.
     */
    public static void printExpensesChart(String title, Map<String, Double> expenses, double unit) {
        System.out.printf("%s (* = %.0f грн):%n", title, unit);
        expenses.forEach((key, sum) ->
                System.out.printf("  %-25s %-40s %.2f%n", key, buildBar(sum, unit), sum));
    }

    /** Рядок із символів '*' для суми: ceil(sum / unit). */
    public static String buildBar(double sum, double unit) {
        if (sum <= 0 || unit <= 0) {
            return "";
        }
        return "*".repeat((int) Math.ceil(sum / unit));
    }

    private static String format(Transaction t) {
        return String.format("%s  %.2f  %s", t.getDate(), t.getAmount(), t.getDescription());
    }
}
