import java.util.ArrayList;

public class Scheduler extends Thread{

    //check in with TA; may I remove " = new Variable[Size];" and do assignment in constructor
    private ArrayList<Command> commands;
    private Process[] process;
    private int NumOfCores;
    private int NumOfProcess;
    private Clock clock;

    Scheduler(Process[] process, int NumOfCores, int NumOfProcess, ArrayList<Command> commands, Clock clock) {
        this.process = process;
        this.NumOfCores = NumOfCores;
        this.NumOfProcess = NumOfProcess;
        this.commands = commands;
        this.clock = clock;
    }

    void Store(String VariableID, int Value) {}

    void Release(String VariableID) {}

    void Lookup(String VariableID) {}

    public void run() {
        int time = clock.getValue();
    }
}
