package com.udav.foldernotification;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;

public class LogDialog extends JDialog {
	private DefaultListModel listModel; 
	private ArrayList<ItemLog> arrayLogs = new ArrayList<ItemLog>();
	
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
        
        //dublclick
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
            	JList list = (JList)evt.getSource();
            	if (evt.getClickCount() == 2) {
                    if (arrayLogs.get(list.getSelectedIndex()).getType()) 
                    	openFile(new File(arrayLogs.get(list.getSelectedIndex()).getFullPath()));
                }
            }
        });
        
        updateContent();
        setModal(true);
        setResizable(false);
        add(list);
	}
	
	public void updateContent() {
		arrayLogs = Log.getLogs();
		listModel.removeAllElements();
		for (int i=0; i<arrayLogs.size(); i++) {
			listModel.addElement(arrayLogs.get(i).getDescription());
		}
	}
	private void openFile(File file) {
	    try {
	        String osName = System.getProperty("os.name");
	        String[] cmd = new String[3];
	 
	        if (osName.equals("Windows 95")) {
	            cmd[0] = "command.com";
	        } else {
	            cmd[0] = "cmd.exe";
	        }
	        cmd[1] = "/C";
	        cmd[2] = file.getAbsolutePath();
	        Runtime rt = Runtime.getRuntime();
	        rt.exec(cmd);
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	}
	
}
