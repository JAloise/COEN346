import java.io.*;
import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {
        //variables read and initialized with values read from input files
        int NumOfCores = 0;
        int NumOfPages = 0;
        int K = 0; 
        int timeout = 0; 

        //selecting input files
        File memconfig = new File("memconfig.txt");  //opening memconfig file
        File commands = new File("commands.txt");  //opening commands file
        File processes = new File("processes.txt");  //opening processes file

        try{
            Scanner sc1 = new Scanner(memconfig);  //using scanner to read tokens from memconfig.txt
            while (sc1.hasNextLine()) {  
                NumOfPages = sc1.nextInt();     //Num of of pages initialized
                K = sc1.nextInt();              //K initialized
                timeout =sc1.nextInt();         //Timeout initialized
            }
            sc1.close();

            Scanner sc2 = new Scanner(commands);  //using scanner to read tokens from commands.txt
            ArrayList<Command> commandsList = new ArrayList<>(); //ArrayList of Command Objects
            while (sc2.hasNextLine()) {
                String str = sc2.next();    
                //create variable objects that will be passed to the constructor of children objects
                if(str == "Store") {    
                    //attribute of variables is read using scanner
                    int id = sc2.nextInt();  
                    int val = sc2.nextInt();
                    Variable var = new Variable(id, val);
                    Command s = new Store(var, str);  //creating Store objects
                    commandsList.add(s);    //adding created object to list
                } else if(str == "Release") {
                    int id = sc2.nextInt();
                    Variable var = new Variable(id);
                    Command r = new Release(var, str);  //creating release objects
                    commandsList.add(r);    //adding created object to list
                } else if(str == "Lookup") {
                    int id = sc2.nextInt();
                    Variable var = new Variable(id);
                    Command l = new Lookup(var, str);    //creating lookup objects
                    commandsList.add(l);    //adding created object to list
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
                    NumOfCores = sc3.nextInt();     //reading NumOfCores
                    sc3.nextInt();
                    skip = true; 
                }
                String ID = sc3.next();
                int startTime = sc3.nextInt();
                int Duration = sc3.nextInt();
                Process p = new Process(ID, startTime, Duration, mmu, commandsList);   //passing Prpcess attributes and MMU object and List of commands
                Processeses.add(p);   
            }
            for(Command c : commandsList) {
                System.out.println(c.printType());
            }
            sc3.close();


            Scheduler Scheduler = new Scheduler(Processeses, NumOfCores);   //schduler thread object
            Thread SchdulerThread = new Thread(Scheduler);  //thread started
            SchdulerThread.start();

            //joining threads in the reverse order the threads were started
            SchdulerThread.join();
            
        } catch (FileNotFoundException e) {
            e.getMessage();
        } 
    }
}
