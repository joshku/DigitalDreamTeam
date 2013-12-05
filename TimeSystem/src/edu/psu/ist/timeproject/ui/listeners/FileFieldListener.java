package edu.psu.ist.timeproject.ui.listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class FileFieldListener implements ActionListener {

	private JLabel errorLabel; 
	
	public FileFieldListener(JLabel errorLabel) {
		this.errorLabel = errorLabel; 
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		JTextField field = (JTextField) arg0.getSource(); 
		
		if (!field.getText().isEmpty() && !new File(field.getText()).exists()) {
			errorLabel.setForeground(Color.RED.darker().darker());
			errorLabel.setText("Error!");
		}
		else {
			errorLabel.setForeground(Color.GREEN.darker().darker()); 
			errorLabel.setText("Valid Directory!");
		}

	}

}
