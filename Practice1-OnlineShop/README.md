# Інтернет-магазин на Java (Практична робота 1)

Консольна програма, що імітує роботу інтернет-магазину: перегляд каталогу,
додавання/видалення товарів у кошику, оформлення замовлень.

## Класи
- `Category` — категорія товару
- `Product` — товар (id, назва, ціна, опис, категорія)
- `Cart` — кошик (додавання, видалення, загальна сума)
- `Order` — замовлення, сформоване з кошика
- `Main` — текстове меню (цикл `while` + `switch`)

Конструктори, гетери та сетери згенеровані через **Lombok**
(`@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`).

## Запуск
- IntelliJ IDEA: відкрити папку як Maven-проєкт, запустити `shop.Main`
  (Settings → Build → Compiler → Annotation Processors → Enable annotation processing).
- Консоль: `mvn compile exec:java`

Вимоги: JDK 17+, Maven.
