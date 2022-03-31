public class Clock extends Thread{

    //value incremented by value very period (clock time unit)
    private int value;

    //clock constructor
    Clock() {
        value = 0;
    }

    //returns clock value: current clock value * clock periodicity
    public int getValue() {
        return (value*50);
    }

    //implementation of run method of the clock thread
    public void run() {
        while (true){
            try {
                Thread.sleep(50);   //clock periodicity
                value = value + 1 ; //vlock value is incremented every 50 ms
            } catch (InterruptedException e) {
                e.getStackTrace();
            }
        }
    }
}
