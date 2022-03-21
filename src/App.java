import java.io.*;
import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {

        File file = new File("input.txt");  //opening input file
        
        try{
            Scanner sc = new Scanner(file);  //using scanner to read tokens from input file
            int size = sc.nextInt();         //first line of code is the number of processes
            int i = 0;
            String IDs[] = new String[size];    //array of process IDs
            int Arrivals[] = new int[size];     //array of Arrival times
            int Bursts[] = new int[size];       //array of burst times
            int Priorities[] = new int[size];   //array of priorities
            while (sc.hasNextLine() && (i<size)) {  //populating arrays 
                IDs[i] = sc.next();     
                Arrivals[i] = Integer.parseInt(sc.next());
                Bursts[i] = Integer.parseInt(sc.next());
                Priorities[i] = Integer.parseInt(sc.next()); 
                i++;
            }
            sc.close();
            
            Clock clock = new Clock();      //creating clock object
            Process processes[] = new Process[size];    //creating array of processes that holds all process objects

            //creating process objects from array of input file parameters and filling array of processes
            for(int j = 0 ; j < size; j++) {
                processes[j] = new Process(IDs[j], Arrivals[j], Bursts[j], Priorities[j], clock);
            }

            //crating thread object for clock class
            Thread ClockThread = new Thread(clock);
            ClockThread.start();
        
            //creating thread object for scheduler class from scheduler object
            Scheduler scheduler = new Scheduler(processes, clock, size);//shared variables: processes array + clk + size
            Thread SchedulerThread = new Thread(scheduler);

            //start scheduler after 1 second has elapsed
            boolean bool = true;
            while (bool)
            {
                int time = 0;
                Thread.sleep(10);
                time = clock.getValue();
                if(time == 1000){
                    SchedulerThread.start();
                    bool = false;
                }
            }

        } catch (FileNotFoundException e) {
            e.getMessage();
        }   
    }
}
