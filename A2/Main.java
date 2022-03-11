import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        
        Queue<Process> expired = new Queue<Process>();
        Queue<Process> active = new Queue<Process>();        
        File file = new File("input.txt");
        try {
            Scanner sc = new Scanner(file);
            int size = sc.nextInt();
            String IDs[] = new String[size];
            int Arrivals[] = new int[size];
            int Bursts[] = new int[size];
            int Priorities[] = new int[size];
            int j = 0;
            while (sc.hasNextLine() && j < (size+1)) {
                IDs[j] = sc.next();
                Arrivals[j] = sc.nextInt();
                Bursts[j] = sc.nextInt();
                Priorities[j] = sc.nextInt(); 
                j++;
            }
            sc.close();
            Process processes[] = new Process[size];
            //create process objects and place them in array of processes
            for(int i = 0 ; i < size; i++) {
                Process Process = new Process(IDs[i], Arrivals[i], Bursts[i], Priorities[i]);
                processes[i] = Process;
            }

		    Process temp;
		
            //sort array of processs based on process priorities
		    for(int i = 0 ; i < processes.length ; i++) {
		        for (int k = i+1 ; k < processes.length ; k++) {
		            if (processes[i].GetPriority() > processes[k].GetPriority()) {
		                temp = processes[i];
		                processes[i] = processes[k];
		                processes[k] = temp;
		            }
		        }
		    }

            //enqueu sorted processes in array 
            for(int i = 0 ; i < size; i++) {
                expired.enqueue(processes[i]);
            }
            
            //Display sorted array elements' process IDs
		    System.out.println("\n" + "SORTED");
            String display = expired.Display();
            System.out.println(display);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
