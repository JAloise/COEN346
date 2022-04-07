import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Scheduler extends Thread{

    //check in with TA; may I remove " = new Variable[Size];" and do assignment in constructor
    private ArrayList<Command> commands;
    private ArrayList<Thread> CreatedThreads;
    private ArrayList<Process> Processes;
    private int NumOfCores;
    private int CommandIndex = 0;
    private int time = 0;


    Scheduler(ArrayList<Process> Processes, int NumOfCores, ArrayList<Command> commands) {
        this.NumOfCores = NumOfCores;
        this.commands = commands;
        this.Processes = Processes;
    }

    public void run() {
        int cTime = MyClock.getInstance().getTime();

        Comparator<Process> c = (p1, p2) -> ((Integer)p1.getStartTime()).compareTo((Integer)p2.getStartTime());
        Processes.sort(c);

        for( Process p : Processes) {
            System.out.println(p.getPID());
        }
             
        boolean finishProg = false;

        while(!finishProg) 
        {
            for(Process p : Processes){
                //check p Process start time; compare (>=) to cTime (current Time)
                // check numofcores. if >0, create process thread, and start the thread, also add thread object to CreatedThreads ArrayList
                // once thread is started; NumofCores--
            }

            //checking for duration( how long process in execution)
            //remove thread from creathed thread ArrayList,
            
            try {
                Thread.sleep(20);
            } catch (Exception e) { e.printStackTrace(); }
            cTime = MyClock.getInstance().getTime();
      
        }

        for(Thread t : CreatedThreads) {
            try {
                t.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}    