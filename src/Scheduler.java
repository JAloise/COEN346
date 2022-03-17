
public class Scheduler extends Thread{
    
    Process[] processes;
    Clock clk;
    Queue<Process> Q1 = new Queue<Process>();
    Queue<Process> Q2 = new Queue<Process>();
    Process temp;
    int TimeSlot[] = new int[processes.length];
    FillEnqueue fill;
    int index = 0;

    Scheduler(Process[] processes, Clock clk)
    {
        this.processes = processes;
        this.clk = clk;
        Q1.setFlag(false); // flag set to false for expired queue
        Q2.setFlag(true);  // flag set to true for active
        fill = new FillEnqueue(processes,clk,Q1,index); //fill expired queue with arriving processes
        Thread fillThread = new Thread(fill);  
        fillThread.start();
    }
    
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

    public void run() {
        
        if(Q2.isEmpty()) //if queue initially set as active is empty,
        {
            //swap flags
            boolean temp;
            temp = Q2.getFlag();       
            Q2.setFlag(Q1.getFlag());
            Q1.setFlag(temp);
            //Q1 is now the active queue and Q2 is the expired queue
        } else {
            if(Q2.getFlag()==true)
            {
                Process running;
                running = Q2.dequeue();
                Thread RunThread = new Thread(running);  
                RunThread.start();
                processes[index].setState("Started");
            } else {
                Process running;
                running = Q1.dequeue();
                Thread RunThread = new Thread(running);  
                RunThread.start();
                processes[index].setState("Started");
            }

        }

        
        //....
        
        
 
        //Updating Process priority
        //waiting_time = sum_of_waiting_times
        //bonus = [10*waiting_time/(now – arrival_time)]. With [x] = floor(x)
        //New_priority = max(100,min(old_priority - bonus+5,139))
    }

}
