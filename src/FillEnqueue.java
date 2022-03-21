import java.util.concurrent.Semaphore;
public class FillEnqueue extends Thread{

    int size;
    Semaphore s = new Semaphore(3);
    Process[] processes = new Process[size];
    Clock clk;
    Queue<Process> expired = new Queue<Process>();
    int index;

    FillEnqueue(Process[] processes, Clock clk, Queue<Process> expired, int index, int size)
    {
        this.size = size;
        this.processes = processes;
        this.clk = clk;
        this.expired = expired;
        index = 0;
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
