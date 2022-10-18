import processor.Processor;
import processor.ProcessorsLoadBalancer;
import task.Task;

import java.io.*;
import java.util.Arrays;

public class Simulator implements Runnable{

    private final int numberOfProcessors;
    private final int numberOfCycles;
    private final String fileName;

    private final Scheduler scheduler;
    private final ProcessorsLoadBalancer processorsLoadBalancer;
    private final Clock clock;


    public Simulator(int numberOfProcessors, int numberOfCycles, String fileName) {
        this.numberOfProcessors = numberOfProcessors;
        this.numberOfCycles = numberOfCycles;
        this.fileName = fileName;

        this.processorsLoadBalancer = new ProcessorsLoadBalancer();
        this.clock = new Clock();
        this.scheduler = new Scheduler(processorsLoadBalancer);

        clock.addLock(scheduler);
        clock.addLock(this);
    }

    @Override
    public void run() {

        startThreads();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            int numberOfTasks = Integer.parseInt(reader.readLine());

            for(int i = 0 ; i<numberOfTasks ; i++){
                int taskInfo[] = Arrays.stream(reader.readLine().split(" ")).
                        mapToInt(Integer::parseInt).toArray();
                Task task = new Task(taskInfo[0], taskInfo[1], taskInfo[2]);
                synchronized (this){
                    while(clock.getCurrentCycle() != task.getCreationTime())
                        this.wait();
                    scheduler.addTask(task);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        while(clock.getCurrentCycle() <= numberOfCycles){
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        endThreads();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("\n                                                         " +
                "Simulation ended");
        System.exit(0);
    }

    private void startThreads(){
        for(int i = 0 ; i<numberOfProcessors ; i++) {
            Processor processor = new Processor();
            processorsLoadBalancer.addProcessor(processor);
            clock.addLock(processor);
            new Thread(processor).start();
        }

        new Thread(clock).start();
        try{
            Thread.sleep(5);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        new Thread(scheduler).start();

    }
    private void endThreads(){
        processorsLoadBalancer.stopProcessors();
        scheduler.stop();
        clock.stop();
    }
}
