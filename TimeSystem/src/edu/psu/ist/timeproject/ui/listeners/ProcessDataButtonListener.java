package edu.psu.ist.timeproject.ui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import edu.psu.ist.timeproject.DataInput;

public class ProcessDataButtonListener implements ActionListener {

	private JTextField xmlFilesField; 
	private JTextField xlsFilesField; 
	private JProgressBar progressBar; 
	private JLabel outputLabel; 
	
	public ProcessDataButtonListener(JTextField xmlFilesField, JTextField xlsFilesField, JProgressBar progressBar, JLabel outputLabel) {
		this.xmlFilesField = xmlFilesField; 
		this.xlsFilesField = xlsFilesField; 
		this.progressBar = progressBar; 
		this.outputLabel = outputLabel; 
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		DataInput runner = new DataInput(new File(xmlFilesField.getText()), new File(xlsFilesField.getText()), progressBar, outputLabel, (JButton)arg0.getSource()); 
		runner.execute();

//		Runner.run();

	}

}
