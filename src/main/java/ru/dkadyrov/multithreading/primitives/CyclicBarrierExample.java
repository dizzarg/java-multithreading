package ru.dkadyrov.multithreading.primitives;

import java.util.concurrent.CyclicBarrier;
import java.util.stream.IntStream;

/**
 * {@link java.util.concurrent.CyclicBarrier} is a synchronisation aid that allows
 * a set of threads to wait for each other to reach a common barrier point.
 * This means that all the threads reaching a specific point (called as barrier point)
 * will have to wait for other threads to reach the same point.
 * As soon as all the threads have reached the barrier point, all threads to are released to continue.
 */
public class CyclicBarrierExample {

    private static final Integer MAX_TASKS = 3;

    public static void main(String[] args) {
        CyclicBarrier start = new CyclicBarrier(MAX_TASKS, () -> System.out.println("Start."));
        CyclicBarrier part1 = new CyclicBarrier(MAX_TASKS, () -> System.out.println("Part 1 completed."));
        CyclicBarrier part2 = new CyclicBarrier(MAX_TASKS, () -> System.out.println("Part 2 completed."));
        CyclicBarrier finish = new CyclicBarrier(MAX_TASKS, () -> System.out.println("Finished."));
        IntStream.range(0, MAX_TASKS)
                .mapToObj(id -> new Task(Integer.toString(id), start, part1, part2, finish))
                .forEach(task -> new Thread(task).start());
    }
}

class Task implements Runnable {

    private final String name;
    private final CyclicBarrier start;
    private final CyclicBarrier part1;
    private final CyclicBarrier part2;
    private final CyclicBarrier finish;

    Task(String name, CyclicBarrier start, CyclicBarrier part1, CyclicBarrier part2, CyclicBarrier finish) {
        this.name = name;
        this.start = start;
        this.part1 = part1;
        this.part2 = part2;
        this.finish = finish;
    }

    @Override
    public void run() {
        try {
            start.await();
            System.out.println("Prepare stage "+ name +"...");
            part1.await();
            System.out.println("Processing stage "+ name +"...");
            part2.await();
            System.out.println("Final stage "+ name +"...");
            finish.await();
        } catch (BrokenBarrierException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/* Output:
Start.
Prepare stage 2...
Prepare stage 0...
Prepare stage 1...
Part 1 completed.
Processing stage 1...
Processing stage 2...
Processing stage 0...
Part 2 completed.
Final stage 0...
Final stage 1...
Final stage 2...
Finished.
*/