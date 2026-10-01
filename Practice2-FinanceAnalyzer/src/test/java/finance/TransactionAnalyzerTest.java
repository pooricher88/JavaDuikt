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
}
