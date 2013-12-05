package edu.psu.ist.timeproject.ui.listeners;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class FileFieldKeyListener implements KeyListener {

	private JLabel errorLabel; 
	public FileFieldKeyListener(JLabel errorLabel) {
		this.errorLabel = errorLabel; 
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		JTextField field = (JTextField) arg0.getSource(); 
		
		if (!field.getText().isEmpty() && !new File(field.getText()).exists()) {
			errorLabel.setForeground(Color.RED.darker().darker());
			errorLabel.setText("Invalid Directory!");
		}
		else {
			errorLabel.setForeground(Color.GREEN.darker().darker()); 
			errorLabel.setText("Valid Directory!");
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
