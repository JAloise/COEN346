import java.io.FileWriter;
import java.io.IOException;

public class Scheduler extends Thread{

    //scheduler class attributes
    int size;   //shared variable: processes array size given from App.java 
    Process[] processes = new Process[size]; //shared list: processes array shared from App.java 
    Clock clk; //shared object: clock 
    Queue<Process> Q1 = new Queue<Process>();   //data structure for queues
    Queue<Process> Q2 = new Queue<Process>();   
    int index = 0;        //index relating to processes array
    Process running;      // Running process that is put inside the CPU for execution
    

    //process constructor" intializes shared objects and queue flags as well as create FillEnqueue object
    Scheduler(Process[] processes, Clock clk, int size)
    {
        this.size = size;
        this.processes = processes;
        this.clk = clk;
        Q1.setFlag(false); // flag set to false for expired queue
        Q2.setFlag(true);  // flag set to true for active
        FillEnqueue fill = new FillEnqueue(processes,clk,Q1,index, size); //fills expired queue with arriving processes
        Thread fillThread = new Thread(fill);  //FillEnqueue will populate the expired queue with arriving processes
        fillThread.start();                    // in a separate thread that runs concurrently with the scheduler thread
    }

    //method calcultaed the timeslot that is to be allocated to each process based on process priority
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

    //method that updates the priority of process after executing for an even number of times (every two times)
    void UpdatePriority(Process p) {
        //waiting_time = sum_of_waiting_times = (start_time - arrival_time) + (resume_time - (start_time + exec_time)) 
        int waiting = (p.GetStart_time()-p.getArrival_time())+(p.getResume_time()-(p.GetStart_time()+p.getExecTime()));
        //bonus = floor([10*waiting_time/(now – arrival_time)]). With [x] = floor(x). now --> clk.getValue
        double bonus = Math.floor( (10 * waiting )/ ( clk.getValue() - p.getArrival_time() ) );
        //New_priority = max(100,min(old_priority - bonus+5,139))
        
        int min;
        int cal = p.GetPriority() - (int)bonus + 5;	// (int)bonus casts the double into an integer
        if( cal > 139 ) {   // finding min(old_priority - bonus + 5, 139)
            min = 139;
        } else {
            min = cal;
        }
        
        if(min > 100) {     // finding new priority value
            p.setPriority(min);
        }
        else {
           p.setPriority(100);
        }
    }

    //function to redirect output to output.txt file
    public void writeToFile(String s) throws IOException {
        FileWriter out = new FileWriter("output.txt",true);
        out.write(s);
        out.flush();
        out.close();
    }

    //function to sort queue based on updated priorites: same function as iin FillEnqueue.java
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

    //run implementation
    public void run() {
                    
        Queue<Process> active;
        Queue<Process> expired;
        //String CurrentState = "";
        while( !( Q2.isEmpty() && Q1.isEmpty() ) ){ //&& (CurrentState != processes[index].GetState()) ){

            //chekcs which queue is active and which one is expired based on flags
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
                Thread RunThread = new Thread(running); //created process thread for running process
                running.setTimeSlot(TimeSlot(running)); //allocated timeslot to process that is about to be run
                running.UpdateExec_time(running.getTimeSlot()); //updated the executed time for running process
                if(running.getcount() == 0) {   //if process executes for the first time
                    running.SetStart_time(clk.getValue());  //get process start time
                    running.setState("Started");            //set state to "Started"
                    //CurrentState = running.GetState();    
                } else{                             
                    running.setResume_time(clk.getValue()); //set resume time if process not running for first time
                    running.setState("Resumed");            //set state tor "Resumed"
                    //CurrentState = running.GetState();
                } 
                RunThread.start();  //start process thread
                // try {
                //     RunThread.join();
                // } catch (InterruptedException e) {
                //     // TODO Auto-generated catch block
                //     e.printStackTrace();
                // }
                System.out.println("done");
                //write to poutput file by calling WriteToFile function and passing string of process parameters
                String s = "Time " + clk.getValue() + ", " + running.getPID() + ", " + running.GetState() + "\n";
                try {
                    writeToFile(s);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //check if process has been executed for an even number of times
                // if (running.getcount() % 2 == 0) {  
                //     UpdatePriority(running); //update priority if process count is even
                // }

                //if process is not finished, put process back into expired queue
                if(running.GetState() != "finished"){
                    expired.enqueue(running);
                }
                //SortQueue(expired);
            }
        }
    }
}