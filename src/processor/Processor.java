package processor;

import task.Task;

public class Processor implements Runnable {

    private static int idCounter = 1;
    private final int id;
    private boolean isBusy;
    private Task currentTask;
    private long currentTaskRemainingTime;
    private boolean simulationRunning;

    public Processor() {
        this.id = idCounter++;
        this.isBusy = false;
    }

    @Override
    public void run() {
        simulationRunning = true;
        while(simulationRunning){
            synchronized (this){
                try{
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if(!isBusy)
                    continue;

                if(currentTaskRemainingTime == 0){
                    System.out.println(
                            "                                          " +
                            "!!!!!! task.Task " + currentTask + " finished on processor " + id + " !!!!!!");
                    setCurrentTask(null);
                }
                currentTaskRemainingTime--;
            }
        }
    }

    public boolean isBusy() {
        return isBusy;
    }
    public void setBusy(boolean busy) {
        isBusy = busy;
    }
    public Task getCurrentTask() {
        return currentTask;
    }

    public synchronized void setCurrentTask(Task currentTask) {
        if(currentTask == null){
            this.currentTask = null;
            this.isBusy = false;
            return;
        }
        this.currentTask = currentTask;
        this.currentTaskRemainingTime = currentTask.getExecutionTime();
        isBusy = true;
        synchronized (this){
            this.notify();
        }
    }
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "processor.Processor{" +
                "id=" + id +
                ", currentTask=" + currentTask +
                '}';
    }


    public void stop() {
        simulationRunning = false;
    }
}
