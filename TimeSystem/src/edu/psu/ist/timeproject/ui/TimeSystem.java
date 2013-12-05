package edu.psu.ist.timeproject.ui;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import edu.psu.ist.timeproject.ui.listeners.BrowseButtonListener;
import edu.psu.ist.timeproject.ui.listeners.FileFieldKeyListener;
import edu.psu.ist.timeproject.ui.listeners.FileFieldListener;
import edu.psu.ist.timeproject.ui.listeners.ProcessDataButtonListener;

public class TimeSystem extends JFrame {
	
	private static final long serialVersionUID = 1569899456108542187L;
	
	private JTextField xmlFilesField; 
	private JTextField xlsFilesField; 
	private JLabel xmlValidationLabel; 
	private JLabel xlsValidationLabel; 
	private JButton xmlBrowseButton; 
	private JButton xlsBrowseButton; 
	private JButton processButton; 
	private JProgressBar progressBar; 
	private JLabel outputLabel; 
	
	public TimeSystem() {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // Default metal is fine...
		}
		
		init(); 
	}
	
	private void init() {
		
		GridBagLayout gbl = new GridBagLayout(); 
		GridBagConstraints gbc = new GridBagConstraints(); 
		this.setLayout(gbl);
		
		gbc.gridy = 0; 
		gbc.gridx = 0; 
		this.add(new JLabel("XML Files Directory: "), gbc); 
		gbc.gridx = 1; 
		xmlFilesField = new JTextField(25);
		this.add(xmlFilesField, gbc); 
		gbc.gridx = 2;  
		xmlBrowseButton = new JButton("Browse"); 
		this.add(xmlBrowseButton, gbc); 

		gbc.gridy = 1; 
		gbc.gridx = 0; 
		gbc.gridwidth = 3; 
		xmlValidationLabel = new JLabel(" "); 
		this.add(xmlValidationLabel, gbc); 
		gbc.gridwidth = 1; 
		
		gbc.gridy = 2; 
		gbc.gridx = 0; 
		this.add(new JLabel("XLS Files Directory: "), gbc); 
		gbc.gridx = 1; 
		xlsFilesField = new JTextField(25);
		this.add(xlsFilesField, gbc); 
		gbc.gridx = 2; 
		xlsBrowseButton = new JButton("Browse"); 
		this.add(xlsBrowseButton, gbc); 
		
		gbc.gridy = 3; 
		gbc.gridx = 0; 
		gbc.gridwidth = 3; 
		xlsValidationLabel = new JLabel(" "); 
		this.add(xlsValidationLabel, gbc); 
		gbc.gridwidth = 1; 
		
		gbc.gridy = 4;
		gbc.gridx = 0;
		gbc.gridwidth = 2; 
		gbc.fill = GridBagConstraints.BOTH; 
		progressBar = new JProgressBar();
		this.add(progressBar, gbc); 
		gbc.gridwidth = 1; 
		
		gbc.gridx = 2; 
		processButton = new JButton("Process Data"); 
		this.add(processButton, gbc); 
		
		gbc.gridy = 5; 
		gbc.gridx = 0; 
		gbc.gridwidth = 2; 
		outputLabel = new JLabel(" ");
		outputLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(outputLabel, gbc); 
		
		xmlFilesField.addActionListener(new FileFieldListener(xmlValidationLabel));
		xlsFilesField.addActionListener(new FileFieldListener(xlsValidationLabel));
		xmlFilesField.addKeyListener(new FileFieldKeyListener(xmlValidationLabel));
		xlsFilesField.addKeyListener(new FileFieldKeyListener(xlsValidationLabel));
		xmlBrowseButton.addActionListener(new BrowseButtonListener(xmlFilesField, this)); 
		xlsBrowseButton.addActionListener(new BrowseButtonListener(xlsFilesField, this)); 
		processButton.addActionListener(new ProcessDataButtonListener(xmlFilesField, xlsFilesField, progressBar, outputLabel));
		
		this.setTitle("Access Data Time Management System");
		this.setPreferredSize(new Dimension(550,200));
		this.setSize(new Dimension(550,200));
		this.setLocationRelativeTo(null);
		xmlFilesField.setText("xmlDropBox");
		xlsFilesField.setText("SpreadsheetDropBox");
        
		this.setVisible(true); 
		
		xmlFilesField.requestFocusInWindow(); 
		validateField(xmlFilesField); 
		
		/* Quick fix: Allow validation to work. */ 
		try {Thread.sleep(100);}
		catch (InterruptedException ie) {}
		
		xlsFilesField.requestFocusInWindow();
		validateField(xlsFilesField); 

	}
	
	private void validateField(JTextField filesField) {
		filesField.requestFocusInWindow(); 
		try {
            Robot robot = new Robot();

            robot.keyPress(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            e.printStackTrace();
        }
	}
	
	public static void main(String args[]) {
		new TimeSystem(); 

	}
}