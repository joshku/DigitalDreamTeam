package edu.psu.ist.timeproject.ui.listeners;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class BrowseButtonListener implements ActionListener {

	private JTextField field;
	private JFrame parent; 
	private JFileChooser jfc = new JFileChooser("."); 
	
	public BrowseButtonListener(JTextField field, JFrame parent) {
		this.field = field; 
		this.parent = parent; 
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		int retval = jfc.showOpenDialog(parent);
		
		if (retval == JFileChooser.APPROVE_OPTION) {
			field.setText(jfc.getSelectedFile().getAbsolutePath());
            field.requestFocusInWindow(); 
			try {
                Robot robot = new Robot();

                robot.keyPress(KeyEvent.VK_ENTER);
            } catch (AWTException e) {
                e.printStackTrace();
            }
		}

	}

}
