package edu.psu.ist.timeproject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import edu.psu.ist.timeproject.spreadsheet.SpreadsheetReader;
import edu.psu.ist.timeproject.xml.XMLReader;

public class Runner {
	
	public static void main(String args[]) throws IOException {
		XMLReader xmlReader = new XMLReader(); 
		SpreadsheetReader ssReader = new SpreadsheetReader(); 
		ArrayList<TimeReport> reports = new ArrayList<>(); 
		for (File file : new File("xmlDropBox").listFiles()) 
			reports.addAll(xmlReader.read(file)); 
		
		for (File file : new File("SpreadsheetDropBox").listFiles()) 
			reports.addAll(ssReader.read(file));
		
//		int i = 0; 
//		for (TimeReport report : reports) {
//			report.print();
//			i++; 
//		}
//		System.out.println(i + " Total Reports"); 
		
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("project"); 
		for (TimeReport report : reports) 
			report.toXML(root);
		
        // lets write to a file
		OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(
            new FileWriter( "output.xml" ), format
        );
        writer.write( document );
        writer.close();
		
	}

}
