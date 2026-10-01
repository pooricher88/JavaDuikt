package finance;

import java.util.List;
import java.util.Map;

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
}
