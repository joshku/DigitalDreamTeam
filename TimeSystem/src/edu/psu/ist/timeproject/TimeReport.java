package edu.psu.ist.timeproject;

import java.util.Calendar;

import javax.xml.datatype.Duration;

import org.dom4j.Element;

import edu.psu.ist.timeproject.util.TimeFormatFactory;

public class TimeReport {
	
	private int projectID; 
	private Status projectStatus; 
	private int clientID; 
	private TimeRecord timeRecord; 
	private int employeeID; 
	private String employeeName;
	private Calendar dateCreated; 
	
	public TimeReport ( int projectID, Status projectStatus, int clientID, Calendar startTime, Calendar endTime, Duration totalTime, int employeeID, String employeeName, Calendar dateCreated) {
		this.projectID = projectID; 
		this.projectStatus = projectStatus; 
		this.clientID = clientID; 
		this.timeRecord = new TimeRecord(startTime, endTime, totalTime); 
		this.employeeID = employeeID; 
		this.employeeName = employeeName; 
		this.dateCreated = dateCreated; 
	}
	
	public void print() {
		System.out.println("Project ID: " + projectID); 
		System.out.println("Project Status: " + projectStatus); 
		System.out.println("Client ID: " + clientID); 
		timeRecord.print();
		System.out.println("Employee ID: " + employeeID); 
		System.out.println("Employee Name: " + employeeName); 
		System.out.println("Date Created: " + (dateCreated != null ? TimeFormatFactory.dateFormat.format(dateCreated.getTime()) : "")); 
		System.out.print("\n"); 
	}
	
	public void toXML(Element projectElement) {
		Element timeReportElement = projectElement.addElement("time_report"); 
		Element projectIDElement = timeReportElement.addElement("project_ID"); 
		projectIDElement.setText(String.valueOf(projectID));
		Element projectStatusElement = timeReportElement.addElement("project_status"); 
		projectStatusElement.setText(projectStatus.name());
		Element clientIDElement = timeReportElement.addElement("client_ID"); 
		clientIDElement.setText(String.valueOf(clientID));
		Element timeRecordElement = timeReportElement.addElement("time_record"); 
		timeRecord.toXML(timeRecordElement); 
		Element employeeIDElement = timeReportElement.addElement("employee_ID");
		employeeIDElement.setText(String.valueOf(employeeID));
		Element employeeNameElement = timeReportElement.addElement("employee_name"); 
		employeeNameElement.setText(employeeName); 
		Element dateCreatedElement = timeReportElement.addElement("date_created"); 
		dateCreatedElement.setText((dateCreated != null ? TimeFormatFactory.dateFormat.format(dateCreated.getTime()) : ""));
	}
}
