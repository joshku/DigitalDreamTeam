package edu.psu.ist.timeproject.xml;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import edu.psu.ist.timeproject.Status;
import edu.psu.ist.timeproject.TimeReport;
import edu.psu.ist.timeproject.util.TimeFormatFactory;

public class XMLReader {
	
	public static Document parse(File file) throws DocumentException {
		SAXReader reader = new SAXReader(); 
		Document doc = reader.read(file); 
		return doc; 
	}
	
	private static String getNodeText(Node node, String xpath) {
		if (node.selectNodes(xpath).size() != 0) 
			return node.selectNodes(xpath).get(0).getText();
		else 
			return null; 
	}
	
	private Integer getIntegerValue(String str) {
		Integer value = null; 

		try {
			value = Integer.parseInt(str); 
		}
		catch(NumberFormatException nfe) {
			value = null; 
		}
		
		return value; 
	}
	
	public ArrayList<TimeReport> read(File file) {
		ArrayList<TimeReport> reports = new ArrayList<TimeReport>(); 
		
		try {
			Document doc = parse(file); 
			Element root = doc.getRootElement(); 

			for (Iterator<?> iter = root.elementIterator(); iter.hasNext();) {
				Element element = (Element) iter.next();

				Integer projectID = getIntegerValue(getNodeText( element, "project_ID" )); 
				Status projectStatus = getNodeText( element, "project_status" ).equals("close") ? Status.closed : Status.open; 
				Integer clientID = getIntegerValue(getNodeText( element, "client_ID" )); 
				
				String clientName = getNodeText(element, "client_name"); 
				
				Node timeRecord = element.selectNodes("time_record").get(0); 
				
				Calendar startTime = Calendar.getInstance(); 
				startTime.setTime( TimeFormatFactory.timeFormat.parse( getNodeText( timeRecord, "project_start_time" ) ) ); 
				
				Calendar endTime = Calendar.getInstance(); 
				endTime.setTime( TimeFormatFactory.timeFormat.parse( getNodeText( timeRecord, "project_end_time" ) ) ); 
				
				DatatypeFactory dtFactory;
				Duration duration = null; 
	
				dtFactory = DatatypeFactory.newInstance();
				String durationString = getNodeText( timeRecord, "project_total_time" ); 
				
				if (!durationString.isEmpty())
					duration = dtFactory.newDuration( getNodeText( timeRecord, "project_total_time" ) ); 
	
				Integer employeeID = getIntegerValue(getNodeText( element, "employee_ID" )); 
				String employeeName = getNodeText( element, "employee_name" ); 
				
				Calendar dateCreated = Calendar.getInstance(); 
				dateCreated.setTime( TimeFormatFactory.dateFormat.parse( getNodeText( element, "date_created" ) ) ); 
				
				TimeReport report = new TimeReport( projectID, projectStatus, clientID, clientName, startTime, endTime, duration, employeeID, employeeName, dateCreated); 
				reports.add( report );								
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (ParseException e1) {
			e1.printStackTrace();
		} catch (DatatypeConfigurationException e2) {
			e2.printStackTrace();
		}
		
		return reports; 
	}
}
