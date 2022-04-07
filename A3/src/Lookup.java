public class Lookup implements Command {

	private int id;
	
	Lookup() {};
	
	Lookup(int id) {
		this.id =id;
	}
	
	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}
	
	public void run() {
		// implement lookup operation HERE
	}

}
