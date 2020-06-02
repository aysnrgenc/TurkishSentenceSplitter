package com.NLP;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TurkishSentenceSplitter {
	   
   static String str; 

   public static void main(String []args) throws Exception{
	   //Read arguments
	   String inputFile = args[0]; 
	   String outputFile = args[1]; 
	   
	   //Create output file
	   PrintWriter writer = new PrintWriter(outputFile);
	   
	   //Read input file
	   str = readFile(inputFile);
	   
	   //Regex for quotation mark
	   findReplaceDOT("\"(\\.|[^\"])*\"");
	   
	   //Regex for date
	   findReplaceDOT("((\\d+)(\\.)(\\d+)(\\.)(\\d{4}))");
	   
	   //Regex for decimal numbers
	   findReplaceDOT("\\d+(\\.)(\\d+)?");
	   
	   //Regex for url 
	   findReplaceDOT("((https://?|ftp://|file://|www)[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])");
	   
	   //Regex for email addresses
	   findReplaceDOT("([_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))");
	   
	   
	   System.out.println(str);
	   
	   
	   
	   String[] SENTENCE = str.split("([\\.]{2,})|(\\.)|(\\!)|(\\?)");
	    for (int i=0;i<SENTENCE.length-1;i++)
	    {
	    	SENTENCE[i] = SENTENCE[i].replaceAll("DOTDOT","\\.");
	    	SENTENCE[i] = SENTENCE[i].replaceAll("INTERJECTIONINTERJECTION","\\!");
	    	SENTENCE[i] = SENTENCE[i].replaceAll("QUESTIONQUESTION","\\?");
	        System.out.println("Cümle " + (i+1) + ": " + SENTENCE[i]);
	        writer.println("Cümle " + (i+1) + ": " + SENTENCE[i]);

	    }
	  
	    writer.close();
	   
   }
   
   	 static void findReplaceDOT(String regex) {
   		 
   		 Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
   		 Matcher m = p.matcher(str);
   		 String trash = "";
   		 
   		 while(m.find()) {
	   		 trash = m.group();
			 trash = trash.replaceAll("\\.", "DOTDOT");
			 trash = trash.replaceAll("\\!", "INTERJECTIONINTERJECTION");
			 trash = trash.replaceAll("\\?", "QUESTIONQUESTION");
			 String x = m.group();
			 str= str.replace(m.group(), trash);
   		 }
   		 
   	 }  
   	 
   	 
	 static String readFile(String fileName) throws IOException {
		    BufferedReader br = new BufferedReader(new FileReader(fileName));
		    try {
		        StringBuilder sb = new StringBuilder();
		        String line = br.readLine();
	
		        while (line != null) {
		            sb.append(line);
		            sb.append("\n");
		            line = br.readLine();
		        }
		        return sb.toString();
		    } finally {
		        br.close();
		    }
		}
	}
