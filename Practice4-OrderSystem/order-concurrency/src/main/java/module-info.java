/** Модуль керування потоками обробки замовлень. */
module order.concurrency {
    requires transitive order.processing;

    exports orders.concurrency;
}
