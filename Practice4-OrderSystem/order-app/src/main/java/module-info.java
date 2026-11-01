/**
 * Модуль застосунку. Бачить лише експортовані пакети інших модулів:
 * orders.model, orders.storage, orders.processing, orders.concurrency.
 * Пакети *.internal недоступні — спроба імпорту дасть помилку компіляції.
 */
module order.app {
    requires order.concurrency;
    requires javafaker;
}
