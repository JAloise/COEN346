
public class Store implements Command {

	private int value;
	private int ID;
	
	Store(int ID, int value) {
		this.ID = ID;
		this.value = value;
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public void run() {
		// implement store operation HERE
	}

}
