public class Queue<T> {
	//queue data structure
	//queue flag used to distingiushed active queue from expired queue
	private boolean flag; //active queue has flag set to true, expired queue has flag set to false

	//first and last node of queue
	private Node first, last;
	//number of elements in queue
	int numofelements;
	
	//queue Node
	private class Node {
		Process process;
		Node next;
	}
	
	//method checks if queue is empty
	public boolean isEmpty() {
		return first == null;
	}
	
	//method returns queue flag
	public boolean getFlag() {
		return flag;
	}

	//method sets queue flag
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	//method to add elements to queue
	public void enqueue(Process process) {
		Node oldlast = last;
		last = new Node();
		last.process = process;
		last.next = null;
		if(isEmpty()) {
			first = last;
		}
		else {
			oldlast.next = last;
		}
		numofelements++;
	}

	//method checks if element exists in queue
	public boolean ExistsInQueue(Process p){
		boolean bool = false;
		Node current = first;
		while(current != null){
			if(current.process.getPID() == p.getPID()) {
				bool = true;
			} else {
				bool = false;
			}
			current = current.next;
		}  
		return bool;
	}
	
	//method to remove elements from queue
	public Process dequeue() {
		Process process = first.process;
		first = first.next;
		numofelements--;
		if(isEmpty())
			last = null;
		return process;
	}

	//method to display queue elements, returns String
	public String Display() {
        String output = "";
        Node current = first;
        while(current != null) {
            output += current.process.getPID() + " ";
            current = current.next;
        }
        return output;
    }
}