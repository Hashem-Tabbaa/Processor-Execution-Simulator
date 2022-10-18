import java.util.ArrayList;
import java.util.List;

public class ProcessorsLoadBalancer{

    private List<Processor> processors;

    public ProcessorsLoadBalancer() {
        processors = new ArrayList<>();
    }

    public boolean assignTask(Task task){
        for(Processor processor : processors){
            if(processor.isBusy()) {
                continue;
            }
            System.out.println("                                           " +
                    "<----  Task " + task + " assigned to processor "
                    +processor.getId()+"  ---->");
            processor.setCurrentTask(task);
            return true;
        }
        return false;
    }

    public void addProcessor(Processor processor){
        new Thread(processor).start();
        processors.add(processor);
    }

    public void stopProcessors() {
        for(Processor processor : processors){
            processor.stop();
        }
    }
}
