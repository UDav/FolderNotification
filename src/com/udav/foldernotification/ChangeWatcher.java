package com.udav.foldernotification;

import java.awt.TrayIcon;
import java.io.File;

public class ChangeWatcher implements Runnable {

	private TrayIcon trayIcon;
	private String[] currentFileList, oldFileList;
	private String pathFolder;
	private int timeOneCycle;
	private boolean stoped;
	
	public ChangeWatcher(TrayIcon trayIcon, String pathFolder) {
		this.trayIcon = trayIcon;
		this.pathFolder = pathFolder;
		this.timeOneCycle = 10000;
	}
	
	public ChangeWatcher(TrayIcon trayIcon, String pathFolder, int timeOneCycle) {
		this(trayIcon, pathFolder);
		this.timeOneCycle = timeOneCycle;
	}
	
	private void scanFolder() {
		currentFileList = new File(pathFolder).list();
		
	}
	
	private boolean[] compareArray(String first[], String second[]) {
		boolean found[] = new boolean[first.length]; 
		for (int i=0; i<first.length; i++) {
			for (int j=0; j<second.length; j++) {
				if (first[i].equals(second[j])) {
					found[i] = true;
				}
			}
		}
		return found;
	}
	
	@Override
	public void run() {
		scanFolder();
		oldFileList = currentFileList;
		while (!stoped) {
			scanFolder();
					
			boolean found[];
			found = compareArray(currentFileList, oldFileList);
			
			String result = "";
			for (int i=0; i<currentFileList.length; i++){
				if (!found[i]){
					result += " + "+currentFileList[i]+"\n";
					if (new File(pathFolder+"/"+currentFileList[i]).isDirectory())
						Log.addToArrayLog(pathFolder, currentFileList[i], true, true);
					else 
						Log.addToArrayLog(pathFolder, currentFileList[i], true, false);
				}
			}
			
			found = compareArray(oldFileList, currentFileList);
			
			
			for (int i=0; i<oldFileList.length; i++){
				if (!found[i]){
					result += " - "+oldFileList[i]+"\n"; 
					if (new File(pathFolder+"/"+oldFileList[i]).isDirectory())
						Log.addToArrayLog(pathFolder, oldFileList[i], false, true);
					else
						Log.addToArrayLog(pathFolder, oldFileList[i], false, false);
				}
			}
			
			if (result != "") {
				trayIcon.displayMessage("Изменения в "+pathFolder, result, TrayIcon.MessageType.INFO);
			}
						
			oldFileList = currentFileList;
			try {
				Thread.sleep(timeOneCycle);
			} catch (Exception e) {e.printStackTrace();}
		}
	}
	
	public void stop(){
		stoped = true;
	}
	
	public String getPathFolder() {
		return pathFolder;
	}

}
