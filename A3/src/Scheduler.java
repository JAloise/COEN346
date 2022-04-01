import java.util.ArrayList;

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

    public void run() {
        time = clock.getValue();
        Queue<Process> active;
        Queue<Process> expired;
        Process running;
        //String CurrentState = "";
        while(!( Q2.isEmpty() && Q1.isEmpty())){ 

            //chekcs which queue is active and which one is expired based on flags
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
            }
        } 
    }
}
