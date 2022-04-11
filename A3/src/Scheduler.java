import java.util.ArrayList;
import java.util.Comparator;

public class Scheduler extends Thread{

    private ArrayList<Process> Processes;   //Shared variable: ArrayList of processes
    private int NumOfCores;                 //Shared variable: Number of cores

    //class constructor
    Scheduler(ArrayList<Process> Processes, int NumOfCores) {
        this.NumOfCores = NumOfCores;
        this.Processes = Processes;
    }

    public void run() {
        //sort Process object in ArrayList of processes in order of start time
        Comparator<Process> c = (p1, p2) -> ((Integer)p1.getStartTime()).compareTo((Integer)p2.getStartTime());
        Processes.sort(c);
        boolean finishProg = false;
        ArrayList<Thread> CreatedThreads = new ArrayList<Thread>();//create ArrayList of commands
        Thread clockThread = new Thread(MyClock.getInstance()); //clock thread object
        clockThread.start();    //thread started
        int cTime = MyClock.getInstance().getTime();
        CreatedThreads.add(clockThread);//Add clock thread to createdThread ArrayList
        while(!finishProg) 
        {   
            for(Process p : Processes) {
                int start_time = p.getStartTime();
                int exec_time = cTime - start_time;
                if(!(p.isProcStarted())){
            	    start_time = p.getStartTime();
                    exec_time = cTime - start_time;
                    if( NumOfCores > 0 ) { 
                        //if there are any available cores
                        if( (start_time >= cTime) ) { //check is process start time exceeds current clock value
                            //check p Process start time; compare (>=) to cTime (current Time)
                            // check NumOfCores. if > 0, create process thread, and start the thread, also add thread object to CreatedThreads ArrayList
                            // once thread is started; NumofCores--
                            Thread processThread = new Thread(p);   //create process object
                            processThread.start();     
                            p.setProcStarted(true);             //start process object
                            String ev = "Clock: " + start_time + ", Process " + p.getPID() + ": Started\n"; 
                            MyClock.PrintEvent(ev); //pass print event to clock intance's PrintEvent method
                            CreatedThreads.add(processThread);  //add thread to arrayList of threads
                            NumOfCores--;   //decrement number of cores
                        }
                    }
                } else {
                    if(exec_time >= p.getDuration() && (!p.isFinishProc())) {  //if process has exceeded its execution time
                        String ev = "Clock: " + (start_time+exec_time) + ", Process " + p.getPID() + ": Finished\n"; 
                        MyClock.PrintEvent(ev); //pass print event to clock intance's PrintEvent method
                        p.setFinishProc(true);  //finish process
                        NumOfCores++;   //release Core
                    }
                }
            }
 
            try {
                Thread.sleep(25);
            } catch(Exception e) {e.printStackTrace();}
            cTime = MyClock.getInstance().getTime();    //get clock value by calling method getTime using the instance of clock
        }

        //join all threads in createdThreads arrayList for Thread safety
        for(Thread t : CreatedThreads) {
            try {
                t.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}    