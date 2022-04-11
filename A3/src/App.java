import java.io.*;
import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {

        int NumOfCores = 0;
        int NumOfPages = 0;
        int K = 0; 
        int timeout = 0; 

        File memconfig = new File("memconfig.txt");  //opening memconfig file
        File commands = new File("commands.txt");  //opening commands file
        File processes = new File("processes.txt");  //opening processes file

        try{
            Scanner sc1 = new Scanner(memconfig);  //using scanner to read tokens from memconfig.txt
            while (sc1.hasNextLine()) {  
                NumOfPages = sc1.nextInt();     
                K = sc1.nextInt();
                timeout =sc1.nextInt();
            }
            sc1.close();

            Scanner sc2 = new Scanner(commands);  //using scanner to read tokens from commands.txt
            ArrayList<Command> commandsList = new ArrayList<Command>();
            while (sc2.hasNextLine()) {
                String str = sc2.next();
                if(str == "Store") {
                    int id = sc2.nextInt();
                    int val = sc2.nextInt();
                    Variable var = new Variable(id, val);
                    Store s = new Store(var, str);
                    commandsList.add(s);
                } else if(str == "Release") {
                    int id = sc2.nextInt();
                    Variable var = new Variable(id);
                    Release r = new Release(var, str);
                    commandsList.add(r);

                } else if(str == "Lookup") {
                    int id = sc2.nextInt();
                    Variable var = new Variable(id);
                    Lookup l = new Lookup(var, str);
                    commandsList.add(l);
                }  
                sc2.nextLine();
            }
            sc2.close();

            MMU mmu = new MMU(timeout, K, NumOfPages);
            Scanner sc3 = new Scanner(processes);  //using scanner to read tokens from processes.txt
            ArrayList<Process> Processeses = new ArrayList<Process>();
            boolean skip = false;
            while (sc3.hasNextLine()) {  //populating arrays 
                if(!skip) {
                    NumOfCores = sc3.nextInt();     
                    sc3.nextInt();
                    skip = true; 
                }
                String ID = sc3.next();
                int startTime = sc3.nextInt();
                int Duration = sc3.nextInt();
                Process p = new Process(ID, startTime, Duration, mmu, commandsList);   
                Processeses.add(p);   
            }
            sc3.close();

            Thread clockThread = new Thread(MyClock.getInstance());
            clockThread.start();

            Scheduler Scheduler = new Scheduler(Processeses, NumOfCores);
            Thread SchdulerThread = new Thread(Scheduler);
            SchdulerThread.start();

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
