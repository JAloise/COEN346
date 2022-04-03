public class Process extends Thread {

    private int StartTime, Duration, ArrivalTime;
    private int PID = 0;
    private Command com;
    private String state;
    Process(int StartTime, int Duration, Command c, String s) {
        this.StartTime = StartTime;
        this.Duration = Duration;
        this.com = c;
        this.state = s;
    }
    Process(int StartTime, int Duration) {
    	 this.StartTime = StartTime;
         this.Duration = Duration;
    }

    public int getPID() {
        return PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        this.Duration = duration;
    }

    public int getStartTime() {
        return StartTime;
    }

    public void setStartTime(int startTime) {
        this.StartTime = startTime;
    }
    
    public void setCommand(Command c) {
    	this.com = c;
    }
    
    public Command getCommand() {
    	return com;
    }
    
    public String GetState() {
    	return state;
    }
    
    public void setState(String s) {
    	this.state = s;
    }
    
    public int getArrivalTime() {
    	return ArrivalTime;
    }
    
    public void setArrivalTime(int a) {
    	this.ArrivalTime = a;
    }
}