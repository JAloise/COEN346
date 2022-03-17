import java.util.concurrent.Semaphore;
public class FillEnqueue extends Thread{

    Semaphore s;
    Process[] processes;
    Clock clk;
    Queue<Process> expired = new Queue<Process>();
    int index;

    FillEnqueue(Process[] processes, Clock clk, Queue<Process> expired, int index)
    {
        this.processes = processes;
        this.clk = clk;
        this.expired = expired;
        index = 0;
    }

    public void run(){
        while(true)
		{
			try {
				Thread.sleep(10);
                for(int i = 0 ; i < processes.length; i++)
                {
                    if(clk.getValue() == processes[i].getArrival_time())
                    {
                        s.acquire();
                        expired.enqueue(processes[i]);
                        processes[i].setState("Arrived");
                        index = i;
                        s.release();
                    }
                }
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
}
