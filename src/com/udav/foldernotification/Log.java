package com.udav.foldernotification;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Log {
	private static ArrayList<ItemLog> arrayLogs = new ArrayList<ItemLog>();
	
	private Log() {}
	
	public static void readLog(){
		File file = new File("./logs");
		
	    FileInputStream fis = null;
	    InputStreamReader isr = null;
	    BufferedReader br = null;

	    try {
	      fis = new FileInputStream(file);
	      isr = new InputStreamReader(fis, "UTF8");
	      br = new BufferedReader(isr);

	      String tmp;
	      arrayLogs.clear();
	      while ((tmp = br.readLine()) != null) {
	    	  String parseStr[] = tmp.split(";"); 
	    	  arrayLogs.add(new ItemLog(parseStr[0], parseStr[1], parseStr[2], Boolean.parseBoolean(parseStr[3])));
	      }
	      
	      fis.close();
	      isr.close();
	      br.close();

	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}
	
	private static synchronized void writeLog(String text) {
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter("./logs", true));
			out.write(text);
		    out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	    
	} 
	
	public static synchronized void addToArrayLog(String pathFolder, String fileName, boolean type){
		Date now = new Date();
		DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	    String time = formatter.format(now);
		
	    arrayLogs.add(new ItemLog(pathFolder, fileName, time, type));
	    
	    writeLog(pathFolder+";"+fileName+";"+time+";"+type+"\n");
	}
		
	public static ArrayList<ItemLog> getLogs() {
		return arrayLogs;
	}
	
	
}
