import java.lang.Math;

public class Scheduler extends Thread{
    
    Process[] processes;
    Clock clk;
    Queue<Process> Q1 = new Queue<Process>();
    Queue<Process> Q2 = new Queue<Process>();
    int TimeSlot[] = new int[processes.length];
    FillEnqueue fill;
    int index = 0;
    Process running;

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
    
    //sort array of processes based on process priorities
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
    
    void UpdatePriority() {

        //Updating Process priority
    	for(int i = 0 ; i < processes.length ; i++) {
        //waiting_time = sum_of_waiting_times
    	// (start_time - arrival_time) + (resume_time - (start_time + exec_time) 
    	int waiting = ( processes[i].GetStart_time() - processes[i].getArrival_time() ) + 
    				  ( processes[i].getResume_time() - ( processes[i].GetStart_time() + processes[i].getExec_time() ) );
    	//bonus = floor([10*waiting_time/(now – arrival_time)]). With [x] = floor(x). now --> clk.getValue
    	int now = clk.getValue();
    	double bonus = Math.floor( (10 * waiting )/ ( now - processes[i].getArrival_time() ) );
    	//New_priority = max(100,min(old_priority - bonus+5,139))
    	// finding min(old_priority - bonus + 5, 139)
    	int min;
        int cal = processes[i].GetPriority() - (int)bonus + 5;	// (int)bonus casts the double into an integer
        if( cal > 139 ) {
        	min = 139;
        } else {
        	min = cal;
        }
        // computing new_priority
        int new_priority;
        if(min > 100) {
        	new_priority = (int) min;
        }
        else {
        	new_priority = 100;
        }
        // setting the new priority to the process
        processes[i].setPriority(new_priority);
    	}
        // re-sorting the processes array based on the newly calculated priorities
        SortProcessesArray();
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
        while( !( Q2.isEmpty() && Q1.isEmpty() ) ) {
            if(Q2.isEmpty()) //if queue initially set as active is empty,
            {
                //swap flags
                boolean temp;
                temp = Q2.getFlag();       
                Q2.setFlag(Q1.getFlag());
                Q1.setFlag(temp);
                //Q1 is now the active queue and Q2 is the expired queue
            } else {
                if(Q2.getFlag()==false)
                {
                    running = Q2.dequeue();   //dequeue from expired
                    Q1.enqueue(running);      //enqueue to active
                    Thread RunThread = new Thread(running);
                    if(running.getcount() != 0) {
                        running.setState("Resumed"); 
                        running.setResume_time(clk.getValue());
                    }  
                    running.setState("Started");  
                    try {
                        RunThread.join();
                    } catch (InterruptedException e) {
                        System.out.println("thread joining failed");
                    }
                } else {
                    Process running;
                    running = Q1.dequeue();
                    Q2.enqueue(running);
                    Thread RunThread = new Thread(running);
                    if(running.getcount() != 0) {
                        running.setState("Resumed"); 
                        running.setResume_time(clk.getValue());
                    }  
                    running.setState("Started");  
                    try {
                        RunThread.join();
                    } catch (InterruptedException e) {
                        System.out.println("thread joining failed");
                    }
                }    
            }
            if ((processes[index]).getcount() % 2 == 0) {
                UpdatePriority();
            }    
        }
    }
}