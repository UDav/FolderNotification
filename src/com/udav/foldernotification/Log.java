package com.udav.foldernotification;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Log {
	
	private static ArrayList<String> logs = new ArrayList<String>();
	
	private Log() {}
	
	private static void readLog(){
		
	}
	
	private static void writeLog(String text) {
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter("./logs", true));
			out.write(text);
		    out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	    
	} 
	
	public static synchronized void addToLog(String text){
		Date now = new Date();
		DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	    String s = formatter.format(now);
		
	    logs.add(s+" "+text);
		writeLog(s+" "+text);
	}
	
	public static ArrayList<String> getLogs() {
		return logs;
	}
}
