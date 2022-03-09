public class Queue<Process> {

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
	
	public Item dequeue() {
		Process process = first.process;
		first = first.next;
		numofelements--;
		if(isEmpty())
			last = null;
		return process;
	}
}
