import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Process implements Runnable{
    
    private final Semaphore semaphore;  //binary semaphore
    private ArrayList<Command> commands = new ArrayList<>();   //ArrayList of commands   
    private String PID; //process ID
    private int StartTime;  //process start time
    private int Duration;   // process duration
    private int AccessIndex = 0;  //must be updated withing critical section
    private MMU mmu;    //mmu object
    private boolean procStarted = false;
    private boolean finishProc = false; 

    //process class constructor
    Process(String PID, int StartTime, int Duration, MMU mmu, ArrayList<Command> commandsList) {
        semaphore = new Semaphore(1);
        this.PID = PID;
        this.StartTime = StartTime;
        this.Duration = Duration;
        this.mmu = mmu;
        commands = commandsList;
    }
    
    //AddCommands to the arrayList of commands from commandsList Array passed to the contructor
    public void AddCommands(ArrayList<Command> commandsList){
        for(Command command : commandsList){
            commands.add(command);
        }
    }

    //getter/setter methods for Process attributes
    public boolean isProcStarted() { return procStarted; }

    public void setProcStarted(boolean procStarted) { this.procStarted = procStarted; }

    public void setFinishProc(boolean finishProg) { this.finishProc = finishProg; }

    public Boolean isFinishProc() { return finishProc; }

    public String getPID() { return PID; }

    public int getDuration() { return Duration; }

    public int getStartTime() { return StartTime; }

    public void run() {
        while(!finishProc) {
            int cTime = MyClock.getInstance().getTime(); 
            try {
                semaphore.acquire();//critical section: enclosed by binary semaphore acquire() and release() method
                Command running = commands.get(AccessIndex);
                mmu.setCommand(running);
                Thread MMUThread = new Thread(mmu);
                MMUThread.start();
                String ev = "Clock: " + cTime + ", Process " + PID + ", " + running.command + 
                ": Variable " + running.getVarID() + ", Value :" + running.getVarValue() + "\n";
                MyClock.PrintEvent(ev);
                AccessIndex++;
                MMUThread.join();
            } catch (InterruptedException e) {  e.printStackTrace();    }
            finally {   semaphore.release();   }
        }
    }
}