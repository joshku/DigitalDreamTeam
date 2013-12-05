package edu.psu.ist.timeproject;

import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import edu.psu.ist.timeproject.spreadsheet.XLSReader;
import edu.psu.ist.timeproject.ui.filters.XLSFileFilter;
import edu.psu.ist.timeproject.ui.filters.XMLFileFilter;
import edu.psu.ist.timeproject.util.TimeFormatFactory;
import edu.psu.ist.timeproject.xml.XMLReader;

public class DataInput extends SwingWorker<Void, Void> implements PropertyChangeListener {
	
	private XMLReader xmlReader = new XMLReader(); 
	private XLSReader xlsReader = new XLSReader(); 
	private File xmlDir; 
	private File xlsDir; 
	
	private JProgressBar progressBar; 
	private JLabel outputLabel; 
	private JButton processButton; 
	
	public DataInput(File xmlDir, File xlsDir, JProgressBar progressBar, JLabel outputLabel, JButton processButton) {
		this.xmlDir = xmlDir; 
		this.xlsDir = xlsDir; 
		this.progressBar = progressBar; 
		this.outputLabel = outputLabel; 
		this.processButton = processButton; 
	}
	
    /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
        } 
    }

    private void processData() {
		File[] xmlFiles = new File[0]; 
		File[] xlsFiles = new File[0];
		this.addPropertyChangeListener(this);
		setProgress(0); 
		processButton.setEnabled(false); 
		if (xmlDir != null && xmlDir.exists()) {
			xmlFiles = xmlDir.listFiles(new XMLFileFilter()); 
		}
		if(xlsDir != null && xlsDir.exists()) {
			xlsFiles = xlsDir.listFiles(new XLSFileFilter()); 
		}
		
		int totalFiles = xmlFiles.length + xlsFiles.length; 
		progressBar.setMaximum(totalFiles);
		setProgress(0); 
		
		if(xlsDir != null && xlsDir.exists()) {
			for (File file : xlsFiles) {
				ArrayList<TimeReport> reports = xlsReader.read(file);
				outputLabel.setText("Processing file " + file.getName() + "...");
				DataCleanser.cleanseReports(reports);
				File archive = new File(xlsDir.getAbsoluteFile() + File.separator + "archived");
				archive.mkdirs(); 
//				file.renameTo(new File(archive.getAbsolutePath() + File.separator + file.getName().substring(0, file.getName().indexOf(".")) + TimeFormatFactory.fileFormat.format(Calendar.getInstance().getTime()) + ".xls")); 
				setProgress(getProgress() + 1); 
			}
		}
		
		if (xmlDir != null && xmlDir.exists()) {
			for (File file : xmlFiles) {
				ArrayList<TimeReport> reports = xmlReader.read(file); 
				outputLabel.setText("Processing file " + file.getName() + "..."); 
				DataCleanser.cleanseReports(reports); 
				File archive = new File(xmlDir.getAbsoluteFile() + File.separator + "archived");
				archive.mkdirs(); 
//				file.renameTo(new File(archive.getAbsolutePath() + File.separator + file.getName().substring(0, file.getName().indexOf(".")) + TimeFormatFactory.fileFormat.format(Calendar.getInstance().getTime()) + ".xml")); 
				setProgress(getProgress() + 1); 
			}
		}
		
		outputLabel.setText(totalFiles > 0 ? "All files processed!" : "No files to process!");
		int totalProcessed = DataCleanser.passedRecords + DataCleanser.failedRecords; 
		
		if (totalProcessed > 0) {
			JOptionPane.showMessageDialog(null,
				"XML Documents Processed: " + (xmlFiles != null ? xmlFiles.length : 0) + "\n" +
				"XLS Documents Processed: " + (xlsFiles != null ? xlsFiles.length : 0) + "\n" +
				"Total Records Processed: " + totalProcessed + "\n" 
						+ "Records Passed: " + DataCleanser.passedRecords + "\n"
						+ "Records Failed: " + DataCleanser.failedRecords,
				"Processing Completed!",
				JOptionPane.INFORMATION_MESSAGE); 
		}
		DataCleanser.resetStats();
		processButton.setEnabled(true); 
    	
    }
	@Override
	protected Void doInBackground() throws Exception {
		processData(); 
		return null;
	}
	
    @Override
    public void done() {
        Toolkit.getDefaultToolkit().beep();
        progressBar.setValue(progressBar.getMinimum()); 
    }
	
	public static void main(String args[]) throws IOException {
//		XMLReader xmlReader = new XMLReader(); 
//		SpreadsheetReader ssReader = new SpreadsheetReader(); 
//		ArrayList<TimeReport> reports = new ArrayList<TimeReport>(); 
//		for (File file : new File("xmlDropBox").listFiles()) 
//			reports.addAll(xmlReader.read(file)); 
//		for (File file : new File("SpreadsheetDropBox").listFiles()) 
//			reports.addAll(ssReader.read(file));
//		Document document = DocumentHelper.createDocument();
//		Element root = document.addElement("project"); 
//		for (TimeReport report : reports) 
//			report.toXML(root);
//		OutputFormat format = OutputFormat.createPrettyPrint();
//        XMLWriter writer = new XMLWriter(
//            new FileWriter( "output.xml" ), format
//        );
//        writer.write( document );
//        writer.close();	
	}
}
