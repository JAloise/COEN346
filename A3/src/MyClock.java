import java.io.*;

public class MyClock implements Runnable {

    private static MyClock instance;
    private int time;
    private boolean finishProg = false;
    
    public boolean isFinishProg(){
        return finishProg;
    }

    public void setFinishProg(boolean setFinishProg) {
        this.finishProg = setFinishProg;
    }

    //returns clock value: current clock value * clock periodicity
    public int getTime() {
        return time;
    }

    public synchronized static MyClock getInstance() {
        if (instance == null) {
            instance = new MyClock();
        }
        return instance;
    }

    public static synchronized void PrintEvent(String ev) {
        try {
            FileWriter out = new FileWriter("vm.txt",true);
            out.write(ev);
            out.flush();
            out.close();
        } catch (IOException e) { e.printStackTrace(); }
    }
    @Override
    //implementation of run method of the clock thread
    public void run() {
        while (true){
            try {
                Thread.sleep(200);   //clock periodicity
                
            } catch (InterruptedException e) { e.getStackTrace(); }
            time = time + 200 ; //vlock value is incremented every 100 ms
        }
    }
}
