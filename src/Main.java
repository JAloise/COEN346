import java.io.*;
import java.util.*;

public class Main {

    Clock clock = new Clock();

    static int size() {
        int size = 0;
        File file = new File("input.txt");
        try{
            Scanner sc = new Scanner(file);
            size = sc.nextInt();
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return size;
    }
    public static void main(String[] args) throws Exception {

        Clock clock = new Clock();

        File file = new File("input.txt");
        int size = size();
        String IDs[] = new String[size];
        int Arrivals[] = new int[size];
        int Bursts[] = new int[size];
        int Priorities[] = new int[size];
        try {
            Scanner sc = new Scanner(file);
            int j = 0;
            while (sc.hasNextLine() && j < (size+1)) {
                IDs[j] = sc.next();
                Arrivals[j] = sc.nextInt();
                Bursts[j] = sc.nextInt();
                Priorities[j] = sc.nextInt(); 
                j++;
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Process processes[] = new Process[size];
        //create process objects and place them in array of processes
        for(int i = 0 ; i < processes.length; i++) {
            Process Process = new Process(IDs[i], Arrivals[i], Bursts[i], Priorities[i], clock);
            processes[i] = Process;
        }


        //sort array of processs based on process priorities
		Process temp;
		for(int i = 0 ; i < processes.length ; i++) {
		    for (int k = i+1 ; k < processes.length ; k++) {
		        if (processes[i].GetPriority() > processes[k].GetPriority()) {
		            temp = processes[i];
		            processes[i] = processes[k];
		            processes[k] = temp;
		        }
		    }
		}

        Thread clockThread = new Thread(clock);  
        clockThread.start();
        
        Scheduler scheduler = new Scheduler(processes, clock); 
        Thread SchedulerThread = new Thread(scheduler);

        boolean bool = true;
        while (bool)
        {
            int time = 0;
            try {
                Thread.sleep(10);
                time = clock.getValue();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(time == 1000){
                SchedulerThread.start();
                bool = false;
            }
        }

        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        System.setOut(out);
    }
}
