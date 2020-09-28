package ru.dkadyrov.multithreading.locks;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Example of counter with synchronized keyword.
 */
class SynchronizedCounter {

    private int value = 0;

    public synchronized void increment() {
        value++;
    }

    public synchronized void decrement() {
        value--;
    }

    public synchronized int get() {
        return value;
    }
}

public class SynchronizedCounterApplication {

    public static void main(String[] args) throws InterruptedException {
        var counter = new SynchronizedCounter();
        var cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 123_456; i++) {
            cachedThreadPool.execute(counter::increment);
        }
        cachedThreadPool.shutdown();
        cachedThreadPool.awaitTermination(20, TimeUnit.SECONDS);
        System.out.println("Result should be 123456: Actual result is: " + counter.get());
    }

}