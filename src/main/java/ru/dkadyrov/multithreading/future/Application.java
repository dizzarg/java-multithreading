package ru.dkadyrov.multithreading.future;

import java.util.Random;
import java.util.concurrent.*;

/**
 * {@link java.util.concurrent.Callable} and {@link java.util.concurrent.Future}
 * in Java to get results from your threads and to allow
 * your threads to throw exceptions. Also, Future allows you to control your
 * threads, checking to see if they’re running or not, waiting for results and
 * even interrupting them or de-scheduling them.
 */
public class Application {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();

        //anonymous call of Callable
        //return value is Integer
        Future<Integer> future = executor.submit(() -> {
            Random random = new Random();
            int duration = random.nextInt(4000);
            if (duration > 2000) {
                throw new TimeoutException ("Sleeping for too long.");
            }

            System.out.println("Starting ...");
            try {
                Thread.sleep(duration);
            } catch (InterruptedException ignored) {}
            System.out.println("Finished.");
            return duration;
        });

        executor.shutdown();
        try {
            //get returned value from call()
            System.out.println("Result is: " + future.get());

        } catch (InterruptedException ignored) {
        } catch (ExecutionException e) {
            TimeoutException ex = (TimeoutException) e.getCause();
            System.out.println(ex.getMessage());
        }
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }

}

// Output
// Starting ...
// Finished.
// Result is: 1639