import java.util.List;
import java.util.Vector;

public class Clock implements Runnable {

    private long currentCycle;
    private List<Object> locks;
    private boolean simulationRunning;

    public Clock() {
        currentCycle = 1;
        locks = new Vector<>();
    }

    public synchronized long getCurrentCycle(){
        return currentCycle;
    }
    public void addLock(Object object){
        locks.add(object);
    }

    @Override
    public void run() {
        simulationRunning = true;
        while(simulationRunning){
            System.out.println("<------------------------------------------- " +
                    "             Clock Cycle: " + currentCycle + "            ------------------------------------------->");
            for(Object lock : locks){
                synchronized (lock){
                    lock.notify();
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            currentCycle++;
        }
    }

    public void stop() {
        simulationRunning = false;
    }
}
