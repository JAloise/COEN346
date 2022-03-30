
public class Store implements Command {

	public int id;
	public int value;
	
	Store() {}
	
	Store(int i, int v) {
		this.id = i;
		this.value = v;
	}
	
	public int getID() {
		return this.id;
	}
	
	public void setID(int i) {
		this.id = i;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public void setValue(int v) {
		this.value = v;
	}
	
	public void run() {
		// implement store operation HERE
	}

}
