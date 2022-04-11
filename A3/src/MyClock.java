import java.io.*;

public class MyClock implements Runnable {

    private static MyClock instance;    //clock instance
    private int time;                   //time value
        
    //returns clock value: current clock value * clock periodicity
    public int getTime() { return time; }

    //method returning instance of clock
    public synchronized static MyClock getInstance() {
        if (instance == null) {
            instance = new MyClock();
        }
        return instance;
    }

    //method redirects print events to output.txt file
    public static synchronized void PrintEvent(String ev) {
        try {
            FileWriter out = new FileWriter("output.txt",true);
            out.write(ev);
            out.flush();
            out.close();
        } catch (IOException e) { e.printStackTrace(); }
    }
    @Override
    //implementation of run method of the clock thread
    public void run() {
        boolean finishProg = false;
        while (!finishProg){
            try {
                Thread.sleep(50);   //clock periodicity
                
            } catch (InterruptedException e) { e.getStackTrace(); }
            time = time + 50 ; //clock value is incremented every 100 ms
        }
    }
}
