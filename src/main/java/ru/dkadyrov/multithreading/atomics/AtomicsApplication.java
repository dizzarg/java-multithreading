package ru.dkadyrov.multithreading.atomics;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Atomics can be used from the java.util.concurrent.atomic.* package.
 *
 * An atomic operation is a compound action that totally completes out totally
 * fails, not supporting inconsistent values or results during it's execution.
 *
 * Simple atomic counter is used the {@link AtomicInteger} class.
 */
class AtomicCounter {

    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    public void increment() {
        atomicInteger.incrementAndGet();
    }

    public void decrement() {
        atomicInteger.decrementAndGet();
    }

    public int get() {
        return atomicInteger.get();
    }
}

public class AtomicsApplication {

    public static void main(String[] args) throws InterruptedException {
        var counter = new AtomicCounter();
        var cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 987_654; i++) {
            cachedThreadPool.execute(counter::increment);
        }
        cachedThreadPool.shutdown();
        cachedThreadPool.awaitTermination(20, TimeUnit.SECONDS);
        System.out.println("Result should be 987654: Actual result is: " + counter.get());
    }

}
