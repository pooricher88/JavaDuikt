package finance;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Проект "Фінансовий аналізатор".
 * Main лише запускає читання, аналіз і звітування — уся логіка в окремих класах.
 */
public class Main {

    private static final String DEFAULT_PATH = "https://informer.com.ua/dut/java/pr2.csv";

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        // Можна передати свій шлях до файлу або URL першим аргументом
        String filePath = args.length > 0 ? args[0] : DEFAULT_PATH;

        // Задача 1: читання файлу та виведення змісту
        List<Transaction> transactions = TransactionCSVReader.readTransactions(filePath);
        System.out.println("Прочитано транзакцій: " + transactions.size());
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
        System.out.println();

        // Задача 2: загальний баланс
        double totalBalance = TransactionAnalyzer.calculateTotalBalance(transactions);
        TransactionReportGenerator.printBalanceReport(totalBalance);

        // Задача 3: кількість транзакцій за місяць
        String monthYear = "01-2024";
        int count = TransactionAnalyzer.countTransactionsByMonth(transactions, monthYear);
        TransactionReportGenerator.printTransactionsCountByMonth(monthYear, count);
        System.out.println();

        // Етап 4: 10 найбільших витрат
        TransactionReportGenerator.printTopExpenses(TransactionAnalyzer.findTopExpenses(transactions));
        System.out.println();

        // Етап 5: на що витрачено найбільше
        TransactionReportGenerator.printExpensesByCategory(
                TransactionAnalyzer.calculateExpensesByCategory(transactions));
    }
}
