package finance.source;

import java.io.IOException;
import java.util.List;

/**
 * Задача 1.2* (самостійна): джерело даних, незалежне від формату та місця зберігання.
 * Відповідає лише за ЧИТАННЯ рядків; ОБРОБКОЮ займається TransactionCSVReader.
 */
public interface DataSource {
    List<String> readLines() throws IOException;
}
