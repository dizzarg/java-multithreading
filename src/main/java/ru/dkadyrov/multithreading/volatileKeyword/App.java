package ru.dkadyrov.multithreading.volatileKeyword;

/**
 * Volatile Keyword, <em>“… the volatile modifier guarantees that any thread that
 * reads a field will see the most recently written value.”</em> - Josh Bloch
 */
import java.util.Scanner;

class Job implements Runnable {

    private volatile boolean running = true;

    public void run() {
        while (running) {
            System.out.println("Running");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown() {
        running = false;
    }
}

public class App {

    public static void main(String[] args) {
        Job job = new Job();
        new Thread(job).start();
        // Wait for the enter key
        System.out.println("Enter any key to stop the thread,");
        System.out.println("Volatile variable running will be forced to true :");
        new Scanner(System.in).nextLine();
        job.shutdown();
    }
}