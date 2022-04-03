public class Lookup implements Command {

	public int id;
	
	Lookup() {};
	
	Lookup(int i) {
		this.id =i;
	}
	
	public int getID() {
		return this.id;
	}

	public void setID(int i) {
		this.id = i;
	}
	
	public void run() {
		// implement lookup operation HERE
	}
	
	
	public String getCommandInfo() {
		String s = "Lookup: Variable: " + getID();
		return s;
	}

}
