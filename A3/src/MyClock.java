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
        System.out.println(ev);
    }
    @Override
    //implementation of run method of the clock thread
    public void run() {
        while (true){
            try {
                Thread.sleep(100);   //clock periodicity
                
            } catch (InterruptedException e) { e.getStackTrace(); }
            time = time + 100 ; //vlock value is incremented every 50 ms
            
        }
    }
}
