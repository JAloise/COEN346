public class Clock extends Thread{

    private int value;

    Clock() {
        value = 0;
    }

    public int getValue() {
        return (value*20);
    }

    public void run() {
        while (true){
            try {
                Thread.sleep(20);
                value += 1 ;
            } catch (InterruptedException e) {
                e.getStackTrace();
            }
        }
    }
}
