import java.util.concurrent.Semaphore;
public class FillEnqueue extends Thread{

    int size;   //shared value indicating size of array of processes
    Semaphore s = new Semaphore(1); //semaphore used to enforce synchronization and protect critical section
    Process[] processes = new Process[size];    //shared list: array of processes taken from input
    Clock clk;  //shared object: clock 
    Queue<Process> expired = new Queue<Process>();  //shared data structure; expired queue receiving arriving processes
    int index;  //shared variable; index of array of processes

    //construcor of class which receives all shared variables from scheduler.java class
    FillEnqueue(Process[] processes, Clock clk, Queue<Process> expired, int index, int size)
    {
        this.size = size;
        this.processes = processes;
        this.clk = clk;
        this.expired = expired;
        index = 0;
    }
    //function to sort queue
    // this method dequeues all queue objects into a temporary array (temp1)
    //the temporary array then sorts the processes based on theur priorities using linear sort algorithm
    //processes are then put back into the queue.
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
                    if(clk.getValue() == processes[i].getArrival_time())
                    {
                        if( !(expired.ExistsInQueue(processes[i]) )) {
                            s.acquire();
                            expired.enqueue(processes[i]);
                            processes[i].setState("Arrived");
                            //SortQueue(expired);
                            index = i;
                            s.release();
                        }
                    }
                }
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if(expired.numofelements == size){
                exit = false;
            }
		}
    }
}
