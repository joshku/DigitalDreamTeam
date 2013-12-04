package edu.psu.ist.timeproject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;

import edu.psu.ist.timeproject.spreadsheet.SpreadsheetReader;
import edu.psu.ist.timeproject.ui.filters.XLSFileFilter;
import edu.psu.ist.timeproject.ui.filters.XMLFileFilter;
import edu.psu.ist.timeproject.util.TimeFormatFactory;
import edu.psu.ist.timeproject.xml.XMLReader;

public class Runner {
	
	private static XMLReader xmlReader = new XMLReader(); 
	private static SpreadsheetReader xlsReader = new SpreadsheetReader(); 
	
	public static void run(File xmlDir, File xlsDir) {
		File[] xmlFiles = null; 
		File[] xlsFiles = null;
		if (xmlDir != null && xmlDir.exists()) {
			xmlFiles = xmlDir.listFiles(new XMLFileFilter()); 
			for (File file : xmlFiles) {
				ArrayList<TimeReport> reports = xmlReader.read(file); 
				DataCleanser.cleansReports(reports); 
				File archive = new File(xmlDir.getAbsoluteFile() + File.separator + "archived");
				archive.mkdirs(); 
				file.renameTo(new File(archive.getAbsolutePath() + File.separator + file.getName().substring(0, file.getName().indexOf(".")) + TimeFormatFactory.fileFormat.format(Calendar.getInstance().getTime()) + ".xml")); 
			}
		}
		
		if(xlsDir != null && xlsDir.exists()) {
			xlsFiles = xlsDir.listFiles(new XLSFileFilter()); 
			for (File file : xlsFiles) {
				ArrayList<TimeReport> reports = xlsReader.read(file); 
				DataCleanser.cleansReports(reports);
				File archive = new File(xlsDir.getAbsoluteFile() + File.separator + "archived");
				archive.mkdirs(); 
				file.renameTo(new File(archive.getAbsolutePath() + File.separator + file.getName().substring(0, file.getName().indexOf(".")) + TimeFormatFactory.fileFormat.format(Calendar.getInstance().getTime()) + ".xls")); 
			}
		}
		int totalProcessed = DataCleanser.passedRecords + DataCleanser.failedRecords; 
		JOptionPane.showMessageDialog(null,
				"XML Documents Processed: " + (xmlFiles != null ? xmlFiles.length : 0) + "\n" +
				"XLS Documents Processed: " + (xlsFiles != null ? xlsFiles.length : 0) + "\n" +
				"Total Records Processed: " + totalProcessed + "\n" 
						+ "Records Passed: " + DataCleanser.passedRecords + "\n"
						+ "Records Failed: " + DataCleanser.failedRecords,
				"Processing Completed!",
				JOptionPane.INFORMATION_MESSAGE); 
		
		DataCleanser.resetStats();
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
