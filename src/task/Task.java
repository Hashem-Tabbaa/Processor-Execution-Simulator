package task;

public class Task implements Comparable<Task>{

    private static int idCounter = 1;
    private final int creationTime;
    private final int executionTime;
    private final int priority;
    private final int id;

    public Task(int creationTime, int executionTime, int priority) {
        this.creationTime = creationTime;
        this.executionTime = executionTime;
        this.priority = priority;
        this.id = idCounter++;
    }

    public int getId() {
        return id;
    }

    public int getCreationTime() {
        return creationTime;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return ""+id;
    }

    @Override
    public int compareTo(Task task) {

        // compare by priority
        if(this.priority > task.priority)
            return -1;
        else if(this.priority < task.priority)
            return 1;

        // if they have the same priority, compare by execution time
        if(this.executionTime > task.executionTime)
            return -1;
        else if(this.executionTime < task.executionTime)
            return 1;

        // if they have the same priority and creation time, compare by execution time
        if(this.creationTime < task.creationTime)
            return -1;
        return 1;
    }
}
