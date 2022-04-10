import java.util.ArrayList;
import java.util.Comparator;

public class Scheduler extends Thread{

    private ArrayList<Process> Processes;
    private int NumOfCores;

    Scheduler(ArrayList<Process> Processes, int NumOfCores) {
        this.NumOfCores = NumOfCores;
        this.Processes = Processes;
    }

    public void run() {
        Comparator<Process> c = (p1, p2) -> ((Integer)p1.getStartTime()).compareTo((Integer)p2.getStartTime());
        Processes.sort(c);
        boolean finishProg = false;
        ArrayList<Thread> CreatedThreads = new ArrayList<Thread>();
        while(!finishProg) 
        {
            int cTime = MyClock.getInstance().getTime();
            for(Process p : Processes){
                //check p Process start time; compare (>=) to cTime (current Time)
                // check NumOfCores. if > 0, create process thread, and start the thread, also add thread object to CreatedThreads ArrayList
                // once thread is started; NumofCores--
            	int start_time = p.getStartTime();
                Thread processThread = new Thread(p);
            	if( start_time >= cTime ) {
            		if( NumOfCores > 0 ) {
            			processThread.start();
                        String ev = "Clock: " + cTime + ", Process " + p.getPID() + ": Started\n"; 
                        MyClock.PrintEvent(ev);
            			CreatedThreads.add(processThread);
            			NumOfCores--;
            		}
            	}

                if(cTime >= 7000) {
                    finishProg = true;
                }
            }  
        }

        for(Thread t : CreatedThreads) {
            try {
                t.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}    