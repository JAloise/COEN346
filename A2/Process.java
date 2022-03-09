
public class Process {

	// Attributes
	private int PID;
	private int arrival_time;
	private int burst_time;
	private int priority;
	private String state;		// can use boolean but we have more than 2 states
	
	// Constructors
	public Process() {
		
	}
	
	public Process(int p, int a, int b, int p, String s) {
		this.PID = p;
		this.arrival_time = a;
		this.burst_time = b;
		this.priority = p;
		this.state = s;
	}
	

	// Setters and Getters
	public int getPID() {
		return PID;
	}

	public void setPID(int pID) {
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

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
