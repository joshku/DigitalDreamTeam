package edu.psu.ist.timeproject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;

import edu.psu.ist.timeproject.util.TimeFormatFactory;

public class DataCleanser {
	
	public static int passedRecords = 0; 
	public static int failedRecords = 0; 
	
	public static String parseDuration(String duration) throws ParseException {	
		if (duration.isEmpty())
			return ""; 
		
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(TimeFormatFactory.timeFormat.parse(duration)); 
		String durationStr = "PT";
		durationStr += String.format("%02dH", calendar.get(Calendar.HOUR_OF_DAY)); 
		durationStr += String.format("%02dM", calendar.get(Calendar.MINUTE)); 
		durationStr += String.format("%02dS", calendar.get(Calendar.SECOND)); 
		return durationStr; 	
	}

	public static String getEmployeeNameForID(int id) {
		return DataOutput.queryForStringValue(id, DataOutput.GET_EMPLOY_NAME_SQL); 
	}
	
	public static String getClientNameForID(int id) {
		return DataOutput.queryForStringValue(id, DataOutput.GET_CLIENT_NAME_SQL); 
	}
	
	public static Integer getEmployeeIDForName(String name) {
		return DataOutput.queryForIntValue(name, DataOutput.GET_EMPLOY_ID_SQL); 
	}
	
	public static Integer getClientIDForName(String name) {
		return DataOutput.queryForIntValue(name, DataOutput.GET_CLIENT_ID_SQL); 
	}
	
	public static void resetStats() {
		passedRecords = 0; 
		failedRecords = 0; 
	}
	public static void cleanseReports(ArrayList<TimeReport> reports) {

		boolean isValid; 

		for (TimeReport report : reports) {
			isValid = true; 
			if (report.getClientID() == null && report.getClientName() != null) {
				Integer clientID = getClientIDForName(report.getClientName()); 
				if (clientID != null)
					report.setClientID(clientID);
				else
					isValid = false; 
			}
			
			if (report.getEmployeeID() == null && report.getEmployeeName() != null) {
				Integer employeeID = getEmployeeIDForName(report.getEmployeeName()); 
				if (employeeID != null)
					report.setEmployeeID(employeeID);
				else
					isValid = false;  
			}
			
			if (report.getClientName() == null && report.getClientID() != null) {
				String clientName = getClientNameForID(report.getClientID()); 
				if (clientName != null && !clientName.isEmpty())
					report.setClientName(clientName);
				else
					report.setClientName("N/A");
			}
			
			if (report.getEmployeeName() == null && report.getEmployeeID() != null) {
				String employeeName = getEmployeeNameForID(report.getEmployeeID()); 
				if (employeeName != null && !employeeName.isEmpty())
					report.setEmployeeName(employeeName);
				else
					report.setEmployeeName("N/A");
			}
			
			if (report.getEmployeeID() == null && report.getEmployeeName() == null)
				isValid = false;  
			
			if (report.getClientID() == null && report.getClientName() == null)
				isValid = false;  
			
			if (report.getProjectID() == null) {
				isValid = false;  
			}
			
			TimeRecord timeRecord = report.getTimeRecord();
			if (timeRecord.getDuration() == null) {
				Duration duration = null; 
				if (timeRecord.getStartTime() != null && timeRecord.getEndTime() != null)
					duration = calculateDuration(timeRecord.getStartTime(), timeRecord.getEndTime()); 
				timeRecord.setDuration(duration);
				if(duration == null) isValid = false; 
			}
			
			if (timeRecord.getStartTime() == null && timeRecord.getEndTime() != null && timeRecord.getDuration() != null)
				timeRecord.setStartTime(getStartTime(timeRecord.getEndTime(), timeRecord.getTimeDuration()));
			if (timeRecord.getStartTime() != null && timeRecord.getEndTime() == null && timeRecord.getDuration() != null)
				timeRecord.setEndTime(getEndTime(timeRecord.getStartTime(), timeRecord.getTimeDuration()));
			
			if (!isValid) {
				DataOutput.saveRecord(report, DataOutput.INSERT_FAILED_SQL);
				failedRecords += 1; 
			}
			else {
				DataOutput.saveRecord(report, DataOutput.INSERT_RECORD_SQL);
				passedRecords += 1; 
			}
			
		}
	}
	
	public static Duration calculateDuration(Calendar startTime, Calendar endTime) {
		DatatypeFactory dtFactory;
		try {
			dtFactory = DatatypeFactory.newInstance();
			Duration duration = null; 
			duration = dtFactory.newDuration(endTime.getTimeInMillis() - startTime.getTimeInMillis());
			return duration; 
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null; 

	}
	
	public static Calendar getStartTime(Calendar endTime, Duration duration) {
		Calendar startTime = Calendar.getInstance(); 
		startTime.set(Calendar.HOUR_OF_DAY, endTime.get(Calendar.HOUR_OF_DAY) - duration.getHours());
		startTime.set(Calendar.MINUTE, endTime.get(Calendar.MINUTE) - duration.getMinutes());
		startTime.set(Calendar.SECOND, endTime.get(Calendar.SECOND) - duration.getSeconds());
		return startTime; 
	}
	
	public static Calendar getEndTime(Calendar startTime, Duration duration) {
		Calendar endTime = Calendar.getInstance(); 
		endTime.set(Calendar.HOUR_OF_DAY, startTime.get(Calendar.HOUR_OF_DAY) + duration.getHours());
		endTime.set(Calendar.MINUTE, startTime.get(Calendar.MINUTE) + duration.getMinutes());
		endTime.set(Calendar.SECOND, startTime.get(Calendar.SECOND) + duration.getSeconds());
		return endTime; 
	}
	
}
