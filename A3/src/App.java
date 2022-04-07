import java.io.*;
import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {

        int NumOfCores = 0;
        int NumOfPages = 0;
        int K = 0; 
        int timeout = 0; //command.txt

        File processes = new File("processes.txt");  //opening processes file
        File memconfig = new File("memconfig.txt");  //opening memconfig file
        File commands = new File("commands.txt");  //opening commands file

        try{
            Scanner sc1 = new Scanner(processes);  //using scanner to read tokens from processes.txt
            ArrayList<Process> Processeses = new ArrayList<Process>();
            boolean skip = false;
            while (sc1.hasNextLine()) {  //populating arrays 
                if(!skip) {
                    NumOfCores = sc1.nextInt();     
                    sc1.nextLine();
                    skip = true; 
                }
                String ID = sc1.next();
                int startTime = sc1.nextInt();
                int Duration = sc1.nextInt();
                Process p = new Process(ID,startTime,Duration);   
                Processeses.add(p);   
            }
            sc1.close();

            Scanner sc2 = new Scanner(memconfig);  //using scanner to read tokens from memconfig.txt
            while (sc2.hasNextLine()) {  
                NumOfPages = sc2.nextInt();     
                K = sc2.nextInt();
                timeout =sc2.nextInt();
            }
            sc2.close();

            Scanner sc3 = new Scanner(commands);  //using scanner to read tokens from commands.txt

            ArrayList<Command> command = new ArrayList<Command>();

            while (sc3.hasNextLine()) {
                String str = sc3.next();
                if(str == "Store") {
                    int id = Integer.parseInt(sc3.next());
                    int val = Integer.parseInt(sc3.next());
                    Command c = new Store(id, val);
                    command.add(c);

                } else if(str == "Release") {
                    int id = Integer.parseInt(sc3.next());
                    Command c = new Release(id);
                    command.add(c);

                } else if(str == "Lookup") {
                    int id = Integer.parseInt(sc3.next());
                    Command c = new Lookup(id);
                    command.add(c);
                }  
                sc3.nextLine();
            }
            sc3.close();

            for(Process p: Processeses){
                p.CommandList(command);
            }

            Thread clockThread = new Thread(MyClock.getInstance());
            clockThread.start();

            Scheduler Scheduler = new Scheduler(Processeses, NumOfCores, command);
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
