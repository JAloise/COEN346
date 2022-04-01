import java.io.*;
import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {

        int NumOfCores = 0;
        int NumOfProcess = 0;
        int NumOfPages = 0;
        int K = 0; 
        int timeout = 0; //command.txt

        File processes = new File("processes.txt");  //opening processes file
        File memconfig = new File("memconfig.txt");  //opening memconfig file
        File commands = new File("commands.txt");  //opening commands file

        try{
            Scanner sc1 = new Scanner(processes);  //using scanner to read tokens from processes.txt
            ArrayList<Process> Processeses = new ArrayList<Process>();
            while (sc1.hasNextLine()) {  //populating arrays 
                boolean skip = false;
                if(!skip) {
                    NumOfCores = sc1.nextInt();     
                    NumOfProcess = sc1.nextInt();
                    skip = true; 
                }
                int startTime = sc1.nextInt();
                int Duration = sc1.nextInt();
                Process p = new Process(startTime,Duration);   
                Processeses.add(p);   
            }
            sc1.close();

            Scanner sc3 = new Scanner(memconfig);  //using scanner to read tokens from memconfig.txt
            while (sc3.hasNextLine()) {  
                NumOfPages = sc3.nextInt();     
                K = sc3.nextInt();
                timeout =sc3.nextInt();
            }
            sc3.close();

            Scanner sc4 = new Scanner(commands);  //using scanner to read tokens from commands.txt

            ArrayList<Command> command = new ArrayList<Command>();

            while (sc4.hasNextLine()) {
                String str = sc4.next();
                if(str == "Store") {
                    int id = Integer.parseInt(sc4.next());
                    int val = Integer.parseInt(sc4.next());
                    Command c = new Store(id, val);
                    command.add(c);

                } else if(str == "Release") {
                    int id = Integer.parseInt(sc4.next());
                    Command c = new Release(id);
                    command.add(c);

                } else if(str == "Lookup") {
                    int id = Integer.parseInt(sc4.next());
                    Command c = new Lookup(id);
                    command.add(c);
                }  
                sc4.nextLine();
            }
            sc4.close();

            Clock clock = new Clock();
            Thread clockThread = new Thread(clock);
            clockThread.start();

            Scheduler Scheduler = new Scheduler(Processeses, NumOfCores, command, clock);
            Thread SchdulerThread = new Thread(Scheduler);
            SchdulerThread.start();

            MMU mmu = new MMU(timeout, K, NumOfPages);
            Thread MMUThread = new Thread(mmu);
            MMUThread.start();

            MMUThread.join();
            SchdulerThread.join();
            clockThread.join();
            
        } catch (FileNotFoundException e) {
            e.getMessage();
        } 
    }
}
