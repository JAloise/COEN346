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
            while (sc1.hasNextLine()) {  //populating arrays 
                NumOfCores = sc1.nextInt();     
                NumOfProcess = sc1.nextInt();   
            }
            Process[] Process = new Process[NumOfProcess];
            int i = 0;
            while (sc1.hasNextLine() && (i<NumOfProcess)) { 
                Process[i].setStartTime(sc1.nextInt());    
                Process[i].setDuration(sc1.nextInt());
                i++;
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
            
            int NumOfStoreCommands = 0;
            int NumOfReleaseCommands = 0;
            int NumOfLookupCommands = 0;

            while (sc3.hasNextLine()) { 
                String command = sc3.next();
                switch (command) {
                    case "Store" : NumOfStoreCommands++;
                        break;
                    case "Lookup":  NumOfReleaseCommands++;
                        break;
                    case "Release":  NumOfLookupCommands++;
                        break;
                }
            }
            sc3.close();

            Scanner sc4 = new Scanner(commands);

            Variable[] Store = new Variable[NumOfStoreCommands];
            int StoreIndex = 0;

            while (sc4.hasNextLine()) {
                String command = sc4.next();  
                int id = Integer.parseInt(sc4.next());
                int val = Integer.parseInt(sc4.next());
                if(command ==  "Store") {
                    Variable store = new Variable(id, val);
                    Store[StoreIndex] = store;
                    StoreIndex++;
                }
                sc4.nextLine();
            }
            sc4.close();

            Scanner sc5 = new Scanner(commands);
            Variable[] Release = new Variable[NumOfReleaseCommands];
            Variable[] Lookup = new Variable[NumOfLookupCommands];
            int ReleaseIndex = 0;
            int LookupIndex = 0;

            while (sc5.hasNextLine()) {
                String command = sc5.next();  
                int id = Integer.parseInt(sc5.next());
                if (command == "Release") {
                    Release[ReleaseIndex].setID(id);
                    ReleaseIndex++;
                }
                if (command == "Lookup") {
                    Lookup[LookupIndex].setID(id);
                    LookupIndex++;
                }
            }

            sc5.close();

            System.out.println("ID: " + Store[0].getID() + "var: " + Store[0].getValue());
            System.out.println("ID: " + Release[0].getID() + "var: " + Release[0].getValue());
            System.out.println("ID: " + Lookup[0].getID() + "var: " + Lookup[0].getValue());

            Clock clock = new Clock();
            Thread clockThread = new Thread(clock);
            clockThread.start();

            Scheduler Scheduler = new Scheduler(StoreIndex, ReleaseIndex, LookupIndex, Store, Release, Lookup, clock);
            Thread SchdulerThread = new Thread(Scheduler);
            SchdulerThread.start();

        } catch (FileNotFoundException e) {
            e.getMessage();
        } 
    }
}
