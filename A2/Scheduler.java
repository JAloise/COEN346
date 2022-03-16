import java.util.concurrent.Semaphore;
public class Scheduler extends Thread{
    
    Semaphore s;
    Process[] processes;
    Clock clk;
    static Queue<Process> expired = new Queue<Process>();
    static Queue<Process> active = new Queue<Process>();
    Process temp;
    int TimeSlot[] = new int[processes.length];

    //sort array of processs based on process priorities
    void SortProcessesArray() {
        
		Process temp;
		for(int i = 0 ; i < processes.length ; i++) {
		    for (int k = i+1 ; k < processes.length ; k++) {
		        if (processes[i].GetPriority() > processes[k].GetPriority()) {
		            temp = processes[i];
		            processes[i] = processes[k];
		            processes[k] = temp;
		        }
		    }
		}
    }

    int CheckClock()
    {
        while(true){
            try {
                s.acquire();
                clk.getValue();
                s.release();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    void TimeSlot() {
        for(int i = 0 ; i<processes.length; i++) {
            int timeSlot = 0;

            if( processes[i].GetPriority() < 100)
            {
                timeSlot = (140 - processes[i].GetPriority())*20;
            } else { 
                timeSlot = (140 - processes[i].GetPriority())*5;
            }
            TimeSlot[i] = timeSlot;
        }
    }

    Scheduler(Process[] processes, Clock clk)
    {
        this.processes = processes;
        this.clk = clk;
        expired.setFlag(false);
        active.setFlag(true);
    }

    public void run() { 

        for(int i = 0; i < processes.length; i++) {
            if(processes[i].getArrival_time() == CheckClock())
                expired.enqueue(processes[i]);
        }
        
        if(active.isEmpty())
        {
            boolean temp;
            temp = active.getFlag();
            active.setFlag(expired.getFlag());
            expired.setFlag(temp);
        }
 
        //Updating Process priority
        //waiting_time = sum_of_waiting_times
        //bonus = [10*waiting_time/(now – arrival_time)]. With [x] = floor(x)
        //New_priority = max(100,min(old_priority - bonus+5,139))
    }

}
