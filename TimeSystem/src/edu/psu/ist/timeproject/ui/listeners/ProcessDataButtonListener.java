package edu.psu.ist.timeproject.ui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JTextField;

import edu.psu.ist.timeproject.Runner;

public class ProcessDataButtonListener implements ActionListener {

	private JTextField xmlFilesField; 
	private JTextField xlsFilesField; 
	
	public ProcessDataButtonListener(JTextField xmlFilesField, JTextField xlsFilesField) {
		this.xmlFilesField = xmlFilesField; 
		this.xlsFilesField = xlsFilesField; 
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Runner.run(new File(xmlFilesField.getText()), new File(xlsFilesField.getText()));

	}

}
