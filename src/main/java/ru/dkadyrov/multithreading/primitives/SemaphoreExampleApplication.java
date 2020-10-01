package ru.dkadyrov.multithreading.primitives;

import java.util.concurrent.Semaphore;

/**
 * {@link java.util.concurrent.Semaphore} - restrict the number of threads that can access a resource.
 */
class MyATMThread extends Thread {

	private final String name;
	private final Semaphore semaphore;

	MyATMThread(String name, Semaphore semaphore) {
		this.name = name;
		this.semaphore = semaphore;
	}

	public void run() {
		try {
			System.out.println(name + " : acquiring lock...");
			System.out.println(name + " : available Semaphore permits now: " + semaphore.availablePermits());

			semaphore.acquire();
			System.out.println(name + " : got the permit!");

			try {
				for (int i = 1; i <= 5; i++) {
					System.out.println(name + " : is performing operation " + i
							+ ", available Semaphore permits : "
							+ semaphore.availablePermits());

					// sleep 1 second
					Thread.sleep(1000);

				}
			} finally {
				// calling release() after a successful acquire()
				System.out.println(name + " : releasing lock...");
				semaphore.release();
				System.out.println(name + " : available Semaphore permits now: " + semaphore.availablePermits());
			}
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}

	}

}

public class SemaphoreExampleApplication {

	public static void main(String[] args) {

		/*
			Maximum of people is 4.
			true means whichever thread gets first in the waiting pool (queue)
			waiting to acquire a resource, is first to obtain the permit.
			Note that I called it a pool!
			The reason is when you say "Queue", you're saying that things are
			scheduled to be FIFO (First In First Out) .. which is not always the case
			here!
			When you initialize the semaphore with Fairness, by setting its second
			argument to true, it will treat the waiting threads like FIFO.
			But,
			it doesn't have to be that way if you don't set on the fairness. the JVM
			may schedule the waiting threads in some other manner that it sees best
			(See the Java specifications for that).
		*/
		Semaphore semaphore = new Semaphore(4);

		System.out.println("Total available Semaphore permits : " + semaphore.availablePermits());

		MyATMThread t1 = new MyATMThread("A", semaphore);
		t1.start();

		MyATMThread t2 = new MyATMThread("B", semaphore);
		t2.start();

		MyATMThread t3 = new MyATMThread("C", semaphore);
		t3.start();

		MyATMThread t4 = new MyATMThread("D", semaphore);
		t4.start();

		MyATMThread t5 = new MyATMThread("E", semaphore);
		t5.start();

		MyATMThread t6 = new MyATMThread("F", semaphore);
		t6.start();

	}
}

// Output
//Total available Semaphore permits : 4
//F : acquiring lock...
//E : acquiring lock...
//D : acquiring lock...
//C : acquiring lock...
//A : acquiring lock...
//B : acquiring lock...
//D : available Semaphore permits now: 4
//A : available Semaphore permits now: 4
//B : available Semaphore permits now: 4
//E : available Semaphore permits now: 4
//F : available Semaphore permits now: 4
//C : available Semaphore permits now: 4
//A : got the permit!
//F : got the permit!
//D : got the permit!
//B : got the permit!
//F : is performing operation 1, available Semaphore permits : 0
//D : is performing operation 1, available Semaphore permits : 0
//A : is performing operation 1, available Semaphore permits : 0
//B : is performing operation 1, available Semaphore permits : 0
//D : is performing operation 2, available Semaphore permits : 0
//B : is performing operation 2, available Semaphore permits : 0
//A : is performing operation 2, available Semaphore permits : 0
//F : is performing operation 2, available Semaphore permits : 0
//D : is performing operation 3, available Semaphore permits : 0
//A : is performing operation 3, available Semaphore permits : 0
//B : is performing operation 3, available Semaphore permits : 0
//F : is performing operation 3, available Semaphore permits : 0
//D : is performing operation 4, available Semaphore permits : 0
//F : is performing operation 4, available Semaphore permits : 0
//A : is performing operation 4, available Semaphore permits : 0
//B : is performing operation 4, available Semaphore permits : 0
//D : is performing operation 5, available Semaphore permits : 0
//F : is performing operation 5, available Semaphore permits : 0
//A : is performing operation 5, available Semaphore permits : 0
//B : is performing operation 5, available Semaphore permits : 0
//D : releasing lock...
//F : releasing lock...
//A : releasing lock...
//C : got the permit!
//E : got the permit!
//B : releasing lock...
//C : is performing operation 1, available Semaphore permits : 1
//E : is performing operation 1, available Semaphore permits : 1
//D : available Semaphore permits now: 1
//A : available Semaphore permits now: 1
//F : available Semaphore permits now: 2
//B : available Semaphore permits now: 2
//C : is performing operation 2, available Semaphore permits : 2
//E : is performing operation 2, available Semaphore permits : 2
//C : is performing operation 3, available Semaphore permits : 2
//E : is performing operation 3, available Semaphore permits : 2
//C : is performing operation 4, available Semaphore permits : 2
//E : is performing operation 4, available Semaphore permits : 2
//E : is performing operation 5, available Semaphore permits : 2
//C : is performing operation 5, available Semaphore permits : 2
//C : releasing lock...
//C : available Semaphore permits now: 3
//E : releasing lock...
//E : available Semaphore permits now: 4