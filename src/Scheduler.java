import processor.ProcessorsLoadBalancer;
import task.Task;

import java.util.concurrent.PriorityBlockingQueue;

public class Scheduler implements Runnable {


    private final ProcessorsLoadBalancer processorsLoadBalancer;
    private PriorityBlockingQueue<Task> taskQueue;
    private boolean simulationRunning;

    public Scheduler(ProcessorsLoadBalancer processorsLoadBalancer) {
        taskQueue = new PriorityBlockingQueue<Task>();
        this.processorsLoadBalancer = processorsLoadBalancer;
    }

    public void addTask(Task task){
        System.out.println("                                               " +
                "**** task.Task " + task.getId()
                + " added to the queue ****");
        taskQueue.add(task);
    }

    private boolean flag = false;
    @Override
    public void run() {
        simulationRunning = true;
        while(simulationRunning){
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(taskQueue.isEmpty())
                continue;
            Task task = taskQueue.poll();
            if(!processorsLoadBalancer.assignTask(task))
                taskQueue.add(task);
        }
    }

    public void stop() {
        simulationRunning = false;
    }
}
