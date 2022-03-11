public class Queue<T> {

	private Node first, last;
	int numofelements;
	
	private class Node {
		Process process;
		Node next;
	}
	
	public boolean isEmpty() {
		return first == null;
	}
	
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
	
	public Process dequeue() {
		Process process = first.process;
		first = first.next;
		numofelements--;
		if(isEmpty())
			last = null;
		return process;
	}

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