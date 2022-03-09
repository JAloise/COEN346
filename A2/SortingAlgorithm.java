// ONLY AN EXAMPLE OF WHAT IT SHOULD LOOK LIKE
// NEED TO ADAPT IT TO A QUEUE (INSTEAD OF ARRAY)
// WE NEED TO SORT IT DEPENDING ON THE PRIORITY OF THE PROCESS OBJECTS IN THE QUEUE(S)

    int[] a = new int[] {0,3,1,6,5,9,8};
		int temp;
		
		System.out.println("UNSORTED");
		
		for(int i = 0 ; i < a.length ; i++) {
		    System.out.print(a[i] + " ");
		}
		
		for(int i = 0 ; i < a.length ; i++) {
		    for (int j = i+1 ; j < a.length ; j++) {
		        if (a[i] > a[j]) {
		            temp = a[i];
		            a[i] = a[j];
		            a[j] = temp;
		        }
		    }
		}
		
		System.out.println("\n" + "SORTED");

		for(int i = 0 ; i < a.length ; i++) {
		    System.out.print(a[i] + " ");
		}

// SAMPLE OUTPUT: 0 1 3 5 6 8 9
