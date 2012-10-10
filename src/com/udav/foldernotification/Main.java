package com.udav.foldernotification;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.URISyntaxException;

public class Main {

	private static SettingsFrame sf;
	private static LogFrame lf;
	private static ThreadController tc;
	
	/**
	 * @param args
	 * @throws URISyntaxException 
	 */
	public static void main(String[] args) throws URISyntaxException {
		
		PopupMenu popup = new PopupMenu();
		
		MenuItem exitItem = new MenuItem("Выход");
		MenuItem settingsItem = new MenuItem("Настройка");
		
		exitItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
				
			}
		});
		settingsItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (sf==null) sf = new SettingsFrame(tc);
				sf.setVisible(true);
			}
		});
		
		popup.add(settingsItem);
		popup.add(exitItem);
		
		SystemTray systemTray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit().getImage(Object.class.getResource("/res/Folder.png"));
		TrayIcon trayIcon = new TrayIcon(image,"FolderNotification",popup);
		trayIcon.setImageAutoSize(true);
		
		// double click tray icon 
		trayIcon.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (lf == null) lf = new LogFrame();
				else {
					if (lf.isVisible()) {
						lf.setVisible(false); 
						lf.updateContent();
					} else
					if (!lf.isVisible()) lf.setVisible(true);
				}
			}
		});
		
		try {
			systemTray.add(trayIcon);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		tc = new ThreadController(trayIcon);
		
	}


}
