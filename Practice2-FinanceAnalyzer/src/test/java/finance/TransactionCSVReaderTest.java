package finance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class TransactionCSVReaderTest {

    @Test
    void testParseTransactionsSkipsHeaderAndBadLines() {
        List<String> lines = List.of(
                "date,amount,description",
                "05-12-2023,-7850,Сільпо",
                "",
                "битий рядок",
                "10-12-2023,50000,Зарплата");

        List<Transaction> result = TransactionCSVReader.parseTransactions(lines);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(new Transaction("05-12-2023", -7850.0, "Сільпо"), result.get(0));
    }

    @Test
    void testReadFromCustomDataSource() {
        // Задача 1.2*: джерело даних можна підмінити, логіка обробки не змінюється
        List<Transaction> result = TransactionCSVReader.readTransactions(
                () -> List.of("01-01-2024,100,Дохід", "02-01-2024,-40,Витрата"));

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(60.0, TransactionAnalyzer.calculateTotalBalance(result));
    }
}
