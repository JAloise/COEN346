public class Release implements Command {

	public int id;
	
	Release() {};
	
	Release(int i) {
		this.id =i;
	}
	
	public int getID() {
		return this.id;
	}

	public void setID(int i) {
		this.id = i;
	}
	
	public void run() {
		// implement release operation HERE
	}
	
	public String getCommandInfo() {
		String s = "Release: Variable " + getID();
		return s;
	}

}