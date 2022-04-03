import java.util.ArrayList;
import java.io.*;

public class Scheduler extends Thread{

    //check in with TA; may I remove " = new Variable[Size];" and do assignment in constructor
    private ArrayList<Command> commands;
    private ArrayList<Process> Processes;
    private Queue<Process> Q1;
    private Queue<Process> Q2;
    private int NumOfCores;
    private int NumOfProcess;
    private Clock clock;
    private int CommandIndex = 0;
    private int time = 0;


    Scheduler(ArrayList<Process> Processes, int NumOfCores, ArrayList<Command> commands, Clock clock) {
        this.NumOfCores = NumOfCores;
        this.commands = commands;
        this.clock = clock;
        this.Processes = Processes;
        this.NumOfProcess = Processes.size();
        Queue<Process> Q1 = new Queue<Process>();
        Queue<Process> Q2 = new Queue<Process>();
        Q1.setFlag(false); // flag set to false for expired queue
        Q2.setFlag(true);  // flag set to true for active
        FillEnqueue fill = new FillEnqueue(Processes,clock, Q1); //fills expired queue with arriving processes
        Thread fillThread = new Thread(fill);  //FillEnqueue will populate the expired queue with arriving processes
        fillThread.start();                    // in a separate thread that runs concurrently with the scheduler thread
    }
    
    //function to redirect output to output.txt file
    public void writeToFile(String s) throws IOException {
        FileWriter out = new FileWriter("output.txt",true);
        out.write(s);
        out.flush();
        out.close();
    }

    public void run() {
        time = clock.getValue();
        Queue<Process> active;
        Queue<Process> expired;
        Process running;
        //String CurrentState = "";
        while(!( Q2.isEmpty() && Q1.isEmpty())){ 

            //checks which queue is active and which one is expired based on flags
            if(Q1.getFlag() == true){
                active = Q1;
                expired = Q2;
            } else {
                active = Q2;
                expired = Q1;
            }
            if(active.isEmpty()) //if queue initially set as active is empty,
            {
                //swap flags
                boolean temp;
                temp = active.getFlag();       
                active.setFlag(expired.getFlag());
                expired.setFlag(temp);
            } else {
                running = active.dequeue();   //dequeue from expired
                running.setPID(commands.get(CommandIndex).getID());
                CommandIndex++;
                Thread runningThread = new Thread(running);
                runningThread.start();
                try {
					runningThread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
                if( clock.getValue() >= running.getStartTime() ) {
                	if ( NumOfCores < 2 ) {
                		active.enqueue(running);
                		running.setState("Started");
                		int start_time = clock.getValue();
                		String s = "Clock: " + start_time + ", Process " + running.getPID() + ": " + running.GetState() + "\n";
                		String s1 = "Clock: " + start_time + ", Process " + running.getPID() + ": " + running.getCommand().getCommandInfo() + "\n";
                		try {
							writeToFile(s);
							writeToFile(s1);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                		NumOfCores++;
                	}
                }
                // if process duration ends, dequeue from active and enqueue in expired, and change state to "finished"
                // also decrease number of used cores
                if( clock.getValue() >= (running.getStartTime()+running.getDuration()) ) {
                	int end_time = clock.getValue();
                	running.setState("Finished");
                	expired.enqueue(active.dequeue()); // not sure about this line
                	String s2 = "Clock: " + end_time + ", Process " + running.getPID() + ": " + running.GetState() + "\n";
                	try {
						writeToFile(s2);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	NumOfCores--;
                }
            }
        } 
    }
}