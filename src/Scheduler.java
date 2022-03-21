import java.io.FileWriter;
import java.io.IOException;

public class Scheduler extends Thread{

    //scheduler class attributes
    int size;   //shared variable 
    Process[] processes = new Process[size]; //shared variable 
    Clock clk; //shared variable 
    Queue<Process> Q1 = new Queue<Process>();
    Queue<Process> Q2 = new Queue<Process>();
    int index = 0;
    FillEnqueue fill = new FillEnqueue(processes,clk,Q1,index, size);
    Process running;
    

    Scheduler(Process[] processes, Clock clk, int size)
    {
        this.size = size;
        this.processes = processes;
        this.clk = clk;
        Q1.setFlag(false); // flag set to false for expired queue
        Q2.setFlag(true);  // flag set to true for active
        fill = new FillEnqueue(processes,clk,Q1,index, size); //fill expired queue with arriving processes
        Thread fillThread = new Thread(fill);  
        fillThread.start();
    }

    int TimeSlot(Process p) {
        int timeSlot = 0;
        if( p.GetPriority() < 100)
        {
            timeSlot = (140 - p.GetPriority())*20;
        } else { 
            timeSlot = (140 - p.GetPriority())*5;
        }
        return timeSlot;
    }

    void UpdatePriority(Process p) {
        //waiting_time = sum_of_waiting_times
        // (start_time - arrival_time) + (resume_time - (start_time + exec_time) 
        int waiting = ( p.GetStart_time() - p.getArrival_time() ) + 
                      ( p.getResume_time() - ( p.GetStart_time() + p.getExecTime() ) );
        //bonus = floor([10*waiting_time/(now – arrival_time)]). With [x] = floor(x). now --> clk.getValue
        double bonus = Math.floor( (10 * waiting )/ ( clk.getValue() - p.getArrival_time() ) );
        //New_priority = max(100,min(old_priority - bonus+5,139))
        // finding min(old_priority - bonus + 5, 139)
        int min;
        int cal = p.GetPriority() - (int)bonus + 5;	// (int)bonus casts the double into an integer
        if( cal > 139 ) {
            min = 139;
        } else {
            min = cal;
        }
        // finding new priority value
        if(min > 100) {
            p.setPriority(min);
        }
        else {
           p.setPriority(100);
        }
    }

    public void writeToFile(String s) throws IOException {
        FileWriter out = new FileWriter("output.txt",true);
        out.write(s);
        out.flush();
        out.close();
    }

    public void SortQueue(Queue<Process> expired) {
        Process[] temp1 = new Process[size];
        Process temp;
        for(int i = 0; i<size; i++)
        {
            temp1[i] = expired.dequeue();
        }
        for(int j = 0 ; j < temp1.length ; j++) {
            for (int k = j+1 ; k < temp1.length ; k++) {
                if (temp1[j].GetPriority() > temp1[k].GetPriority()) {
                    temp = temp1[j];
                    temp1[j] = temp1[k];
                    temp1[k] = temp;
                }
            }
        }
        for(int i = 0; i<size; i++)
        {
            expired.enqueue(temp1[i]);
        }
    }

    public void run() {
                    
        Queue<Process> active;
        Queue<Process> expired;
        //String CurrentState = "";
        while( !( Q2.isEmpty() && Q1.isEmpty() ) ){ //&& (CurrentState != processes[index].GetState()) ){

            if(Q1.getFlag() == true){
                active = Q1;
                expired = Q2;
            } else {
                active = Q2;
                expired = Q1;
            }
            if(active.isEmpty()) //if queue initially set as active is empty,
            {
                //swap flags
                boolean temp;
                temp = active.getFlag();       
                active.setFlag(expired.getFlag());
                expired.setFlag(temp);
                //Q1 is now the active queue and Q2 is the expired queue
            } else {
                running = active.dequeue();   //dequeue from expired
                Thread RunThread = new Thread(running);
                running.setTimeSlot(TimeSlot(running));
                running.UpdateExec_time(running.getTimeSlot());
                if(running.getcount() == 0) {
                    running.SetStart_time(clk.getValue());
                    running.setState("Started");
                    //CurrentState = running.GetState();   
                } else{
                    running.setResume_time(clk.getValue());
                    running.setState("Resumed");
                    //CurrentState = running.GetState();
                } 
                RunThread.start();
                // try {
                //     RunThread.join();
                // } catch (InterruptedException e) {
                //     // TODO Auto-generated catch block
                //     e.printStackTrace();
                // }
                System.out.println("done");
                String s = "Time " + clk.getValue() + ", " + running.getPID() + ", " + running.GetState() + "\n";
                try {
                    writeToFile(s);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // if (running.getcount() % 2 == 0) {
                //     UpdatePriority(running);
                // }

                if(running.GetState() != "finished"){
                    expired.enqueue(running);
                }
                //SortQueue(expired);
            }
        }
    }
}