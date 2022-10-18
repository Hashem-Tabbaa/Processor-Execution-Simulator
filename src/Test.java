import java.util.concurrent.PriorityBlockingQueue;

public class Test {

    static Object lock = new Object();
    static Object lock2 = new Object();

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Thread 1");
        }).start();

        new Thread(() -> {
            synchronized (lock2) {
                try {
                    lock2.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Thread 2");
        }).start();

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                lock.notify();
            }
        });
        t2.start();
        t2.join();

        synchronized (lock2) {
            lock2.notify();
        }
    }
}
