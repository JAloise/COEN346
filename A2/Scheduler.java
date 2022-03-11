public class Scheduler extends Thread{
    
    public void run() {

        

        // Calculating time slots
        //Ts = (140-priority)*20 (millisecond) if priority < 100
        //Ts = (140-priority)*5 (millisecond) if priority >= 100

        //Updating Process priority
        //waiting_time = sum_of_waiting_times
        //bonus = [10*waiting_time/(now – arrival_time)]. With [x] = floor(x)
        //New_priority = max(100,min(old_priority - bonus+5,139))
    }

}
