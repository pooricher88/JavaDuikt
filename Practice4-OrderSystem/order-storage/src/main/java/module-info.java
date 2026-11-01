/**
 * Модуль зберігання замовлень.
 * Експортується лише пакет orders.storage; пакет orders.storage.internal закритий для інших модулів.
 */
module order.storage {
    requires transitive order.model;

    exports orders.storage;
}
