package com.udav.foldernotification;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;

public class LogDialog extends JDialog {
	private DefaultListModel listModel; 
	
	public LogDialog() {
		
		setTitle("Изменения:");
		Toolkit kit = Toolkit.getDefaultToolkit() ;
        Dimension screenSize = kit.getScreenSize() ;
        int x = screenSize.width;
        int y = screenSize.height;
        setBounds(x/4, y/4, 600, 300);
		setVisible(true);
		
		listModel = new DefaultListModel();
        JList list = new JList(listModel);
        updateContent();
        setModal(true);
        setResizable(false);
        add(list);
	}
	
	public void updateContent() {
		ArrayList<String> tmp = Log.getLogs();
		listModel.removeAllElements();
		for (int i=0; i<tmp.size(); i++) {
			listModel.addElement(tmp.get(i));
		}
	}
}
