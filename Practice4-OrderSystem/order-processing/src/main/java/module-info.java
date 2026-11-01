/**
 * Модуль обробки замовлень. Внутрішній пакет orders.processing.internal не експортується.
 */
module order.processing {
    requires transitive order.storage;

    exports orders.processing;
}
