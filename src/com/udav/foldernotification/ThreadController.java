package com.udav.foldernotification;

import java.awt.TrayIcon;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class ThreadController {

	private ArrayList<ChangeWatcher> arrayChangeWatchers = new ArrayList<ChangeWatcher>();
	private ArrayList<String> arrayPath = new ArrayList<String>();
	private TrayIcon trayIcon;
	
	public ThreadController(TrayIcon trayIcon) {
		this.trayIcon = trayIcon;
		try {
		loadFromFile();
		} catch(Exception e) {e.printStackTrace();}
		
		for (int i=0; i<arrayPath.size(); i++)
			executeThread(arrayPath.get(i));
	}
	
	public void reconfigure(ArrayList<String> arrayPath){
		this.arrayPath = arrayPath;
		for (int i=0; i<arrayChangeWatchers.size(); i++) {
			arrayChangeWatchers.get(i).stop();
		}
		for (int i=0; i<arrayPath.size(); i++)
			executeThread(arrayPath.get(i));
		saveToFile();
	}
	
	private void executeThread(String path) {
		if (new File(path).exists()) {
			arrayChangeWatchers.add(new ChangeWatcher(trayIcon, path));
			new Thread(arrayChangeWatchers.get(arrayChangeWatchers.size()-1)).start();
		}
		
	}
	
	private void stopThread() {
		
	}
	
	public ArrayList<String> getSettings() {
		return arrayPath;
	}
		
	private void saveToFile() {
		PrintWriter writer = null;
	    try {
	    	File file = new File("./settings");
	    	writer = new PrintWriter(
	             new OutputStreamWriter(
	             new FileOutputStream(file), "UTF8"));
	    	for (int i=0;i<arrayPath.size();i++){
	    		writer.write(arrayPath.get(i)+"\n");
	    	}
	     writer.close();
	    } catch (Exception ex) {ex.printStackTrace();} 
	}
	
	private void loadFromFile() throws URISyntaxException {	
		File file = new File("./settings");
		
	    FileInputStream fis = null;
	    InputStreamReader isr = null;
	    BufferedReader br = null;

	    try {
	      fis = new FileInputStream(file);
	      isr = new InputStreamReader(fis, "UTF8");
	      br = new BufferedReader(isr);

	      String tmp;
	      while ((tmp = br.readLine()) != null) {
	    	arrayPath.add(tmp);
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
}
