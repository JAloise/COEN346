public class Clock extends Thread{

    int value;

    Clock() {
        value = 0;
    }

    public int getValue() {
        return value;
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
