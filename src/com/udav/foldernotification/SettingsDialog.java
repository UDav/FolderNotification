package com.udav.foldernotification;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;

public class SettingsDialog extends JDialog {
	private JButton saveSettings;
	private JButton cancel;
	private JButton add;
	private DefaultListModel listModel;
	private ThreadController tc;
	
	public SettingsDialog(ThreadController tc) {
		this.tc = tc;
		Toolkit kit = Toolkit.getDefaultToolkit() ;
        Dimension screenSize = kit.getScreenSize() ;
        int x = screenSize.width;
        int y = screenSize.height;
        setModal(true);
        setResizable(false);
        setBounds(x/4, y/4, 600, 300);
        setTitle("Настройки");
        
        
        JPanel mainPanel = new JPanel();
        
        listModel = new DefaultListModel();
        JList list = new JList(listModel);
        readSettings();
        list.setLayoutOrientation(JList.VERTICAL);
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {
                    listModel.remove(list.getSelectedIndex());
                }
            }
        });
        
        mainPanel.add(list);
        add = new JButton("Добавить");
        add.setPreferredSize(new Dimension(100, 25));
        mainPanel.add(add);
        
        
        saveSettings = new JButton("Сохранить");
        saveSettings.setPreferredSize(new Dimension(100, 25));
        cancel = new JButton("Отмена");
        cancel.setPreferredSize(new Dimension(100, 25));
        
        ButtonAction action = new ButtonAction();
        saveSettings.addActionListener(action);
        cancel.addActionListener(action);
        add.addActionListener(action);
        
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(saveSettings);
        buttonsPanel.add(cancel);
        
        add(mainPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);
	}
	
	class ButtonAction implements ActionListener {
        @Override
		public void actionPerformed(ActionEvent event) {
            if (event.getSource() == saveSettings) {
            	ArrayList<String> tmp = new ArrayList<String>();
            	for (int i=0; i<listModel.size(); i++){
            		tmp.add(listModel.getElementAt(i).toString());
            	}
            	tc.reconfigure(tmp);
            	dispose();
            }
            
            if (event.getSource() == add) {
            	JFileChooser fileAdd = new JFileChooser();
            	fileAdd.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int ret = fileAdd.showDialog(null, "Добавить папку");				
				if (ret == JFileChooser.APPROVE_OPTION) {
					listModel.addElement(fileAdd.getSelectedFile());
				}
            }
            
            if (event.getSource() == cancel) {
                dispose();
            }
        }
    }
	
	private void readSettings() {
		for (int i=0; i<tc.getSettings().size(); i++)
			listModel.addElement(tc.getSettings().get(i));
	}

}
