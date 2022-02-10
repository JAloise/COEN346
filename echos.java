import java.util.*;
import java.io.*;


public class Main
{
	public static void main(String[] args) throws IOException {
		
		Scanner sc = new Scanner(System.in);
		
		String input = sc.nextLine();
		
		
		if(input.contains("echo") && !input.contains(" ->") && !input.contains(" ->>")) {
		    String toBeDisplayed = echo1(input);
		    System.out.print(toBeDisplayed);
		}
		
		else if (input.contains("echo") && input.contains(" ->") && !input.contains(" ->>") ){ 
		    int index = input.indexOf(">");
		    String filestring = "";
		    for(int i = index+2 ; i < input.length() ; i++) {
		        char a = input.charAt(i);
		        filestring += a;
		    }
		    String toBeDisplayed = echo2(input, index);
		    SingleArrow(toBeDisplayed, filestring);
		}
		
		else if(input.contains("echo") && !input.contains("-> ") && input.contains(" ->>")) {
		    int index = input.lastIndexOf(">");
		    String filestring = "";
		    for(int i = index+2 ; i < input.length() ; i++) {
		        char a = input.charAt(i);
		        filestring += a;
		    }
		    String toBeDisplayed = echo3(input, index);
		    DoubleArrow(toBeDisplayed, filestring);
		}
		
	}
	
	public static String echo1(String input) {
	    String toReturn = "";
	    for(int i = 6 ; i < input.length()-1 ; i++)
	    {
	        char c = input.charAt(i);
	        toReturn += c;
	    }
	    return toReturn;
	}
	
	public static String echo2(String input, int index) {
	    String toReturn = "";
	    for(int i = 6 ; i < index-3 ; i++) {
	        char a = input.charAt(i);
	        toReturn += a;
	    }
	    return toReturn;
	}
	
	public static String echo3(String input, int index) {
	    String toReturn = "";
	    for(int i = 6 ; i < index-4 ; i++) {
	        char a = input.charAt(i);
	        toReturn += a;
	    }
	    return toReturn;
	}
	
	public static void SingleArrow(String echoOutput, String fileName) throws IOException {
	    FileWriter WriteToFile = new FileWriter(fileName);
	    WriteToFile.write(echoOutput);
	    WriteToFile.flush();
	    WriteToFile.close();
	}
	
	public static void DoubleArrow(String echoOutput, String fileName) throws IOException {
	    FileWriter toAppend = new FileWriter(fileName, true);
	    toAppend.write(echoOutput);
	    toAppend.flush();
	    toAppend.close();
	}
}