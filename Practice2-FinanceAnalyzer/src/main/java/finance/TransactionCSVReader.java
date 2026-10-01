package finance;

import finance.source.DataSource;
import finance.source.FileDataSource;
import finance.source.UrlDataSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Читає транзакції з CSV (формат рядка: дата,сума,опис).
 * Абстрактний клас зі статичними методами — екземпляр створювати не потрібно.
 */
public abstract class TransactionCSVReader {

    /** Читає транзакції за шляхом: URL (http/https) або локальний файл. */
    public static List<Transaction> readTransactions(String filePath) {
        DataSource source = filePath.startsWith("http://") || filePath.startsWith("https://")
                ? new UrlDataSource(filePath)
                : new FileDataSource(filePath);
        return readTransactions(source);
    }

    /** Читає транзакції з будь-якого джерела даних (задача 1.2*). */
    public static List<Transaction> readTransactions(DataSource source) {
        try {
            return parseTransactions(source.readLines());
        } catch (IOException e) {
            System.err.println("Помилка читання даних: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /** Обробка рядків CSV у список транзакцій (не залежить від джерела). */
    public static List<Transaction> parseTransactions(List<String> lines) {
        List<Transaction> transactions = new ArrayList<>();
        for (String line : lines) {
            Transaction t = parseLine(line);
            if (t != null) {
                transactions.add(t);
            }
        }
        return transactions;
    }

    /** Перетворює один рядок у Transaction; повертає null для заголовка чи некоректного рядка. */
    public static Transaction parseLine(String line) {
        if (line == null || line.isBlank()) {
            return null;
        }
        String[] values = line.replace("\uFEFF", "").split(",", 3);
        if (values.length < 3) {
            return null;
        }
        try {
            double amount = Double.parseDouble(values[1].trim());
            return new Transaction(values[0].trim(), amount, values[2].trim());
        } catch (NumberFormatException e) {
            return null; // заголовок або пошкоджений рядок
        }
    }
}
