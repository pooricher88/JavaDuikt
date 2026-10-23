package finance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

class TransactionAnalyzerTest {

    @Test
    void testCalculateTotalBalance() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("01-01-2023", 100.0, "Дохід"),
                new Transaction("02-01-2023", -50.0, "Витрата"),
                new Transaction("03-01-2023", 150.0, "Дохід"));

        double result = TransactionAnalyzer.calculateTotalBalance(transactions);

        Assertions.assertEquals(200.0, result, "Розрахунок загального балансу неправильний");
    }

    @Test
    void testCountTransactionsByMonth() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("01-02-2023", 50.0, "Дохід"),
                new Transaction("15-02-2023", -20.0, "Витрата"),
                new Transaction("05-03-2023", 100.0, "Дохід"));

        int countFeb = TransactionAnalyzer.countTransactionsByMonth(transactions, "02-2023");
        int countMar = TransactionAnalyzer.countTransactionsByMonth(transactions, "03-2023");

        Assertions.assertEquals(2, countFeb, "Кількість транзакцій за лютий неправильна");
        Assertions.assertEquals(1, countMar, "Кількість транзакцій за березень неправильна");
    }

    @Test
    void testFindTopExpenses() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("01-01-2024", -10.0, "Кава"),
                new Transaction("02-01-2024", 500.0, "Зарплата"),
                new Transaction("03-01-2024", -300.0, "Оренда"),
                new Transaction("04-01-2024", -50.0, "Продукти"));

        List<Transaction> top = TransactionAnalyzer.findTopExpenses(transactions, 2);

        Assertions.assertEquals(2, top.size());
        Assertions.assertEquals(-300.0, top.get(0).getAmount());
        Assertions.assertEquals(-50.0, top.get(1).getAmount());
    }

    @Test
    void testCalculateExpensesByCategory() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("01-01-2024", -20.0, "Кава"),
                new Transaction("02-01-2024", -30.0, "Кава"),
                new Transaction("03-01-2024", -100.0, "Оренда"),
                new Transaction("04-01-2024", 1000.0, "Зарплата"));

        Map<String, Double> result = TransactionAnalyzer.calculateExpensesByCategory(transactions);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Оренда", result.keySet().iterator().next());
        Assertions.assertEquals(50.0, result.get("Кава").doubleValue());
    }

    @Test
    void testFindLargestAndSmallestExpenseInPeriod() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("05-01-2024", -200.0, "Продукти"),
                new Transaction("10-01-2024", -15.0, "Кава"),
                new Transaction("20-01-2024", 5000.0, "Зарплата"),
                new Transaction("02-02-2024", -900.0, "Оренда"));

        Transaction largest = TransactionAnalyzer
                .findLargestExpenseInPeriod(transactions, "01-01-2024", "31-01-2024").orElseThrow();
        Transaction smallest = TransactionAnalyzer
                .findSmallestExpenseInPeriod(transactions, "01-01-2024", "31-01-2024").orElseThrow();

        Assertions.assertEquals(-200.0, largest.getAmount(), "Найбільша витрата за січень неправильна");
        Assertions.assertEquals(-15.0, smallest.getAmount(), "Найменша витрата за січень неправильна");
        Assertions.assertTrue(TransactionAnalyzer
                .findLargestExpenseInPeriod(transactions, "01-03-2024", "31-03-2024").isEmpty());
    }

    @Test
    void testCalculateExpensesByMonth() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("15-02-2024", -300.0, "Оренда"),
                new Transaction("01-01-2024", -100.0, "Кава"),
                new Transaction("20-01-2024", -50.0, "Кава"),
                new Transaction("25-01-2024", 1000.0, "Зарплата"));

        Map<String, Double> result = TransactionAnalyzer.calculateExpensesByMonth(transactions);

        Assertions.assertEquals(List.of("01-2024", "02-2024"), List.copyOf(result.keySet()));
        Assertions.assertEquals(150.0, result.get("01-2024").doubleValue());
        Assertions.assertEquals(300.0, result.get("02-2024").doubleValue());
    }

    @Test
    void testBuildBar() {
        Assertions.assertEquals("***", TransactionReportGenerator.buildBar(2500.0, 1000.0));
        Assertions.assertEquals("*", TransactionReportGenerator.buildBar(1000.0, 1000.0));
        Assertions.assertEquals("", TransactionReportGenerator.buildBar(0.0, 1000.0));
    }
}
