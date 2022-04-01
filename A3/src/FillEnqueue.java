import java.util.ArrayList;
import java.util.concurrent.Semaphore;
public class FillEnqueue extends Thread{

    private int size;   //shared value indicating size of array of processes
    private Semaphore s = new Semaphore(1); //semaphore used to enforce synchronization and protect critical section
    private ArrayList<Process> Processes;    //shared list: array of processes taken from input
    private Clock clk;  //shared object: clock 
    private Queue<Process> enqueue;  //shared data structure; expired queue receiving arriving processes

    //construcor of class which receives all shared variables from scheduler.java class
    FillEnqueue(ArrayList<Process> Processes, Clock clk, Queue<Process> enqueue)
    {
        this.enqueue = enqueue;  
        this.Processes = Processes;
        this.size = Processes.size();
        this.clk = clk;
    }
    //function to sort queue
    // this method dequeues all queue objects into a temporary array (temp1)
    //the temporary array then sorts the processes based on theur priorities using linear sort algorithm
    //processes are then put back into the queue.

    // Run implementation: thread will constantly check clock and enqueues arriving processes into expired queue
    // Shared variables; queue and index are manipulated inside the critical sections for the sake of synchronization
    // Once queue is filled, thread will reach the end of its execution.
    // Queue is sorted everytime a new process arrives
    public void run(){
        boolean exit = true;
        while(exit)
		{
			try {
				Thread.sleep(10);
                for(int i = 0 ; i < size; i++)
                {
                    if(clk.getValue() >= Processes.get(i).getStartTime())
                    {
                        if( !(enqueue.ExistsInQueue(Processes.get(i)) )) {
                            s.acquire();
                            enqueue.enqueue(Processes.get(i));
                            //SortQueue(expired);
                            s.release();
                        }
                    }
                    if(enqueue.numofelements == size){
                        exit = false;
                    }
                }
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
    }
}
