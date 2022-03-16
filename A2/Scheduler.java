
public class Scheduler extends Thread{
    

    Process[] processes;
    Clock clk;
    Queue<Process> expired = new Queue<Process>();
    Queue<Process> active = new Queue<Process>();
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
                Thread.sleep(10);
                clk.getValue();
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
        FillEnqueue fill = new FillEnqueue(processes,clk,expired);
        Thread fillThread = new Thread(fill);  
        fillThread.start();
    }

    public void run() {

        Process dequeued = expired.dequeue();
        active.enqueue(dequeued);
        Process process = active.dequeue();
        Thread P = new Thread(process);  
        P.start();
        process.setState("started");
        //....
        
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
