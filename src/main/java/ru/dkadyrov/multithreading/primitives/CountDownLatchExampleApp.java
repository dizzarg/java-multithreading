package ru.dkadyrov.multithreading.primitives;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * {@link java.util.concurrent.CountDownLatch} works in latch principle,
 * the main thread will wait until the gate is open. One thread waits
 * for n threads, specified while creating the CountDownLatch.
 *
 * Any thread, usually the main thread of the application, which calls
 * {@link java.util.concurrent.CountDownLatch#await()}  will wait until count reaches zero
 * or it's interrupted by another thread. All other threads are required to count down
 * by calling {@link java.util.concurrent.CountDownLatch#countDown()}
 * once they are completed or ready.
 *
 * As soon as count reaches zero, the waiting thread continues.
 * One of the disadvantages/advantages of {@link java.util.concurrent.CountDownLatch} is
 * that it's not reusable: once count reaches zero
 * you cannot use {@link java.util.concurrent.CountDownLatch} any more.
 */
class Processor implements Runnable {

    private final CountDownLatch latch;

    public Processor(CountDownLatch latch) {
        this.latch = latch;
    }

    public void run() {
        System.out.println("Started.");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {}
        latch.countDown();
    }
}

public class CountDownLatchExampleApp {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            executor.submit(new Processor(latch));
        }
        executor.shutdown();

        try {
            // Application’s main thread waits, till other service threads which are
            // as an example responsible for starting framework services have completed started all services.
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Completed.");
    }

}

// Output:
// Started.
// Started.
// Started.
// Completed.