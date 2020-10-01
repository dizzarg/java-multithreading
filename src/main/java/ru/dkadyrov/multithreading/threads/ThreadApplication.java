package ru.dkadyrov.multithreading.threads;

/**
 * Threads.
 *
 * Java support for OS-threads.
 * Thread is a basic unit that can run in parallel through CPU cores.
 */
public class ThreadApplication {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=============================");
        System.out.println("Starting Threads with extends");
        System.out.println("=============================");
        Runner runner1 = new Runner();
        runner1.start();
        Runner runner2 = new Runner();
        runner2.start();

        // wait until thread on which join has called finished
        runner1.join();
        runner2.join();

        System.out.println("=============================");
        System.out.println("Starting Threads using Runnable Interface");
        System.out.println("=============================");
        Thread thread1 = new Thread(new RunnerRunnable());
        Thread thread2 = new Thread(new RunnerRunnable());
        thread1.start();
        thread2.start();

        // wait until thread on which join has called finished
        thread1.join();
        thread2.join();

        System.out.println("=============================");
        System.out.println("Starting threads using the Thread constructor with lambda");
        System.out.println("=============================");
        Thread lambdaThread1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Hello: " + i + " Lambda Thread: " + Thread.currentThread().getName());

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {}
            }
        });
        Thread lambdaThread2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Hello: " + i + " Lambda Thread: " + Thread.currentThread().getName());

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {}
            }
        });

        lambdaThread1.start();
        lambdaThread2.start();

    }

}

class Runner extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Hello: " + i + " Extended Thread: " + Thread.currentThread().getName());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class RunnerRunnable implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Hello: " + i + " Thread with Runnable: " + Thread.currentThread().getName());

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}