package com.udav.foldernotification;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;

public class LogFrame extends JFrame {
	private DefaultListModel listModel; 
	
	public LogFrame() {
		
		setTitle("Изменения:");
		setBounds(100, 100, 100, 100);
		setVisible(true);
		
		listModel = new DefaultListModel();
        JList list = new JList(listModel);
        updateContent();
        add(list);
	}
	
	public void updateContent() {
		ArrayList<String> tmp = Log.getLogs();
		for (int i=0; i<tmp.size(); i++) {
			listModel.addElement(tmp.get(i));
		}
	}
}
