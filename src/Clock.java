public class Clock extends Thread{

    private int value;

    Clock() {
        value = 0;
    }

    public int getValue() {
        return (value*50);
    }

    public void run() {
        while (true){
            try {
                Thread.sleep(50);
                value = value + 1 ;
            } catch (InterruptedException e) {
                e.getStackTrace();
            }
        }
    }
}
