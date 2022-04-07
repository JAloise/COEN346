import java.util.ArrayList;

public class Process implements Runnable{
    
    private ArrayList<Command> commands;
    private String PID;
    private int StartTime;
    private int Duration;   
    private int AccessIndex = 0;  //must be updated withing critical section

    Process(String PID, int StartTime, int Duration) {
        this.PID = PID;
        this.StartTime = StartTime;
        this.Duration = Duration;
    }

    public void CommandList(ArrayList<Command> commands) {
        this.commands = commands;
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
        int index = 0;
        Command exec = commands.get(index);
    }
}
/*
int CpuTime = 0;
        int ResumeTime = 0;
        int PauseTime = 0;
        ChangeState = true;
        do{
            int cTime = clock.getTime();

            switch(ProcessState) {
                case 1:
                    if(ChangeState) {
                        ResumeTime = cTime;
                        String ev = "Time: "+cTime+", Process "+PID+": Started\n";
                        clock.PrintEvent(ev);
                        ChangeState = false;
                    }
                    CpuTime = CpuTime + (cTime-ResumeTime);
                    ResumeTime = CpuTime;
                    if(CpuTime>=Duration) {
                        ProcessState = 4;
                        String ev = "Time: "+cTime+", Process Finished\n";
                        clock.PrintEvent(ev);
                        break;
                    }
                case 2:
                    break;    
                default:
                    if(ChangeState) {
                        String ev = "Time: "+cTime+", Process Started\n";
                        clock.PrintEvent(ev);
                        ChangeState = false;
                    }
                    break;    
            }
            try {
                Thread.sleep(10);
            } catch( Exception e) { e.printStackTrace();     }
        } while (ProcessState != 4);
        */
