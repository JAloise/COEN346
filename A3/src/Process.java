import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Process implements Runnable{
    
    private final Semaphore semaphore;
    private ArrayList<Command> commands;
    private String PID;
    private int StartTime;
    private int Duration;   
    private int AccessIndex = 0;  //must be updated withing critical section
    private MMU mmu;

    Process(String PID, int StartTime, int Duration, ArrayList<Command> commands, MMU mmu) {
        semaphore = new Semaphore(1);
        this.commands = commands;
        this.PID = PID;
        this.StartTime = StartTime;
        this.Duration = Duration;
        this.mmu = mmu;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String pID) {
        this.PID = pID;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        this.Duration = duration;
    }

    public int getStartTime() {
        return StartTime;
    }

    public void setStartTime(int startTime) {
        this.StartTime = startTime;
    }

    public void run() {
        Boolean finishProcess = false;
        while(!finishProcess) {
            int cTime = MyClock.getInstance().getTime();
            int exec_time = cTime - StartTime; 
            try {
                semaphore.acquire();

                //critical section
                Command running = commands.get(AccessIndex);
                mmu.APICall(running);
                String ev = "Clock: " + cTime + ", Process " + PID + ", " + running.command + 
                            ": Variable " + running.getVarID() + ", Value :" + running.getVarValue() + "\n";
                MyClock.PrintEvent(ev);
                AccessIndex++;

            } catch (InterruptedException e) {  e.printStackTrace();    }
            finally {   semaphore.release();   }
            //checking for duration( how long process in execution)
            //remove thread from CreatedThreads ArrayList,
            if(exec_time >= Duration) {
                String ev = "Clock: " + cTime + ", Process " + PID + ": Finished\n"; 
                MyClock.PrintEvent(ev);
                finishProcess = true;
            }
        }
    }
}