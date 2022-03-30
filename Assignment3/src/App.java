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

            System.out.println("NumOfStoreCommands: " + NumOfStoreCommands);
            System.out.println("NumOfReleaseCommands: " + NumOfReleaseCommands);
            System.out.println("NumOfLookupCommands: " + NumOfLookupCommands);

            Variable[] Store = new Variable[NumOfStoreCommands];
            Variable[] Release = new Variable[NumOfReleaseCommands];
            Variable[] Lookup = new Variable[NumOfLookupCommands];

            int StoreIndex = 0;
            int ReleaseIndex = 0;
            int LookupIndex = 0;
            while (sc3.hasNextLine()) { 
                String command = sc3.next();
                if (command == "Store") {
                    int id = sc3.nextInt();
                    int val = sc3.nextInt();
                    Store[StoreIndex].setID(id);
                    Store[StoreIndex].setValue(val);
                    StoreIndex++;
                }
                if (command == "Release") {
                    int id = sc3.nextInt();
                    int val = sc3.nextInt();
                    Store[ReleaseIndex].setID(id);
                    Store[ReleaseIndex].setValue(val);
                    ReleaseIndex++;
                }
                if (command == "Lookup") {
                    int id = sc3.nextInt();
                    int val = sc3.nextInt();
                    Store[LookupIndex].setID(id);
                    Store[LookupIndex].setValue(val);
                    LookupIndex++;
                }
            }
            
            sc3.close();

        } catch (FileNotFoundException e) {
            e.getMessage();
        } 

        int time = 0;
        Clock clock = new Clock();
        Thread clockThread = new Thread(clock);
        clockThread.start();
        
        // while(true) {
        //     time = clock.getValue();
        //     System.out.println("Clock: " + time + " ms");
        // }
    }
}
