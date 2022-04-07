public class Release implements Command {

	private int id;
	
	Release() {};
	
	Release(int id) {
		this.id =id;
	}
	
	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}
	
	public void run() {
		// implement release operation HERE
	}

}