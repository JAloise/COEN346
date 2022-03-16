public class Process extends Thread {
	// Attributes
	private String PID;
	private int arrival_time;
	private int burst_time;
	private int priority;
	private String state = "Arrived";		// can use boolean but we have more than 2 states
	private int count = 0;
	private int exec_time = 0;
	
	public Process(String id, int a, int b, int p) {
		this.PID = id;
		this.arrival_time = a;
		this.burst_time = b;
		this.priority = p;
	}
	
	public int getExec_time() {
		return exec_time;
	}

	public void setExec_time(int exec_time) {
		this.exec_time = exec_time;
	}

	// Setters and Getters
	public String getPID() {
		return PID;
	}

	public void setPID(String pID) {
		PID = pID;
	}

	public int getArrival_time() {
		return arrival_time;
	}

	public void setArrival_time(int arrival_time) {
		this.arrival_time = arrival_time;
	}

	public int getBurst_time() {
		return burst_time;
	}

	public void setBurst_time(int burst_time) {
		this.burst_time = burst_time;
	}

	public int GetPriority() {
		return priority;
	}

	public void SetPriority(int priority) {
		this.priority = priority;
	}

	public String GetState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void IncrementCount()
	{
		count++;
	}
    
    public void run() {
		setState("started");
		
	}
}
