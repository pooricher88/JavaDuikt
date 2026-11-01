package orders.concurrency;

import orders.model.Order;
import orders.model.OrderStatus;
import orders.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * Розподіляє замовлення між потоками пулу. Дія над замовленням передається
 * як лямбда або посилання на метод (наприклад, processor::processOrder).
 */
public class OrderDispatcher implements AutoCloseable {

    private final ExecutorService pool;
    private final AtomicInteger processed = new AtomicInteger();
    private final AtomicInteger failed = new AtomicInteger();

    public OrderDispatcher(int threads) {
        AtomicInteger counter = new AtomicInteger(1);
        ThreadFactory factory = runnable -> new Thread(runnable, "worker-" + counter.getAndIncrement());
        this.pool = Executors.newFixedThreadPool(threads, factory);
    }

    /** Відправляє кожне замовлення в окреме завдання пулу і чекає завершення всіх. */
    public <T extends Product> void dispatchAndWait(List<Order<T>> orders, Consumer<Order<T>> handler) {
        List<Future<?>> futures = new ArrayList<>();
        for (Order<T> order : orders) {
            Runnable task = () -> {
                try {
                    handler.accept(order);
                    processed.incrementAndGet();
                } catch (RuntimeException e) {
                    order.setStatus(OrderStatus.FAILED);
                    failed.incrementAndGet();
                    throw e;
                }
            };
            futures.add(pool.submit(task));
        }
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (ExecutionException e) {
                System.out.println("Помилка в потоці: " + e.getCause().getMessage());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    public int getProcessed() {
        return processed.get();
    }

    public int getFailed() {
        return failed.get();
    }

    @Override
    public void close() {
        pool.shutdown();
        try {
            if (!pool.awaitTermination(5, TimeUnit.SECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
