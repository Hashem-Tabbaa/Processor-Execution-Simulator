public class Main {

    /**
     * @param args the command line arguments
     *             args[0] - number of processors
     *             args[1] - number of cycles
     *             args[2] - input file path
     */

    // To run the jar file from the command line, use the following command:
    // java -jar <jar file name> <number of processors> <number of cycles> <input file path>
    // Example:
    // java -jar processor-execution-simulator.jar 4 12 ./input.txt
    public static void main(String[] args){

        Simulator simulator = new Simulator(4, 12, "src\\input\\input.txt");
        new Thread(simulator).start();
    }

}
