import java.io.IOException;

public class Main {


    public static void main(String[] args){
        Simulator simulator = new Simulator(2, 12, "src\\input2.txt");
        new Thread(simulator).start();
    }

}
