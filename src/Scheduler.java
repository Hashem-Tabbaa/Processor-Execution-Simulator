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
                if(taskQueue.isEmpty())
                    continue;
                Task task = taskQueue.peek();
                while(processorsLoadBalancer.assignTask(task)){
                    taskQueue.poll();
                    if(taskQueue.isEmpty())
                        break;
                    task = taskQueue.peek();
                }
            }
        }
    }

    public void stop() {
        simulationRunning = false;
    }
}
