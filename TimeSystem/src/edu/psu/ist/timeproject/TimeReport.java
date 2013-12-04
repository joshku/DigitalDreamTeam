package edu.psu.ist.timeproject;

import java.util.Calendar;

import javax.xml.datatype.Duration;

import org.dom4j.Element;

import edu.psu.ist.timeproject.util.TimeFormatFactory;

public class TimeReport {
	
	public Integer getProjectID() {
		return projectID;
	}

	public void setProjectID(Integer projectID) {
		this.projectID = projectID;
	}

	public Status getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(Status projectStatus) {
		this.projectStatus = projectStatus;
	}

	public Integer getClientID() {
		return clientID;
	}

	public void setClientID(Integer clientID) {
		this.clientID = clientID;
	}
	
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public TimeRecord getTimeRecord() {
		return timeRecord;
	}

	public void setTimeRecord(TimeRecord timeRecord) {
		this.timeRecord = timeRecord;
	}

	public Integer getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(Integer employeeID) {
		this.employeeID = employeeID;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Calendar getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Calendar dateCreated) {
		this.dateCreated = dateCreated;
	}

	private Integer projectID; 
	private Status projectStatus; 
	private Integer clientID; 
	private String clientName; 
	private TimeRecord timeRecord; 
	private Integer employeeID; 
	private String employeeName;
	private Calendar dateCreated; 
	
	public TimeReport (Integer projectID, Status projectStatus, Integer clientID, Calendar startTime, Calendar endTime, Duration totalTime, Integer employeeID, String employeeName, Calendar dateCreated) {
		this(projectID, projectStatus, clientID, "N/A", startTime, endTime, totalTime, employeeID, employeeName, dateCreated); 
	}
	
	public TimeReport (Integer projectID, Status projectStatus, Integer clientID, String clientName, Calendar startTime, Calendar endTime, Duration totalTime, Integer employeeID, String employeeName, Calendar dateCreated) {
		this.projectID = projectID; 
		this.projectStatus = projectStatus; 
		this.clientID = clientID; 
		this.clientName = clientName; 
		this.timeRecord = new TimeRecord(startTime, endTime, totalTime); 
		this.employeeID = employeeID; 
		this.employeeName = employeeName; 
		this.dateCreated = dateCreated; 
	}
	
	public void print() {
		System.out.println("Project ID: " + projectID); 
		System.out.println("Project Status: " + projectStatus); 
		System.out.println("Client ID: " + clientID);
		System.out.println("Client Name: " + clientName); 
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
