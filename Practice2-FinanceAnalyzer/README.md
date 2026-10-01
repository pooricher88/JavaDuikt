# Практична робота 2. Фінансовий аналізатор

Програма читає банківські транзакції з CSV (`дата,сума,опис`, дата у форматі `dd-MM-yyyy`)
і виводить звіти. За замовчуванням дані беруться з https://informer.com.ua/dut/java/pr2.csv,
або можна передати свій шлях/URL першим аргументом.

## Що реалізовано
1. Читання CSV та виведення змісту.
2. Загальний баланс — `calculateTotalBalance`.
3. Кількість транзакцій за місяць (`MM-yyyy`) — `countTransactionsByMonth`.
4. 10 найбільших витрат — `findTopExpenses`.
5. Витрати за категоріями (на що витрачено найбільше) — `calculateExpensesByCategory`.

## Структура
- `Transaction` — модель (Lombok `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`).
- `TransactionCSVReader`, `TransactionAnalyzer`, `TransactionReportGenerator` — **абстрактні класи
  зі статичними методами**: вони не мають власного стану, тому створювати їх екземпляри не потрібно.
- `source/DataSource` + `UrlDataSource`, `FileDataSource` — самостійне завдання 1.2*:
  читання даних відокремлене від обробки, тож нове джерело (файл, URL, БД) додається без змін у логіці парсингу.
- `Main` — лише запускає читання, аналіз і звітування.

## Тести (JUnit 5)
`TransactionAnalyzerTest`, `TransactionCSVReaderTest` — баланс, підрахунок за місяць, топ витрат,
категорії, пропуск заголовка/некоректних рядків, підміна джерела даних.

## Запуск
- IntelliJ IDEA: відкрити папку як Maven-проєкт → запустити `finance.Main`; тести — ПКМ по `src/test/java` → Run Tests.
- Консоль: `mvn test`, `mvn compile exec:java`.
