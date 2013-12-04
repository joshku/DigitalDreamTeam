package edu.psu.ist.timeproject;

import java.util.Calendar;

import javax.xml.datatype.Duration;

import org.dom4j.Element;

import edu.psu.ist.timeproject.util.TimeFormatFactory;

public class TimeRecord {
	
	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	private Calendar startTime;
	private Calendar endTime; 
	private Duration duration; 
	
	public TimeRecord( Calendar startTime, Calendar endTime, Duration duration) {
		this.startTime = startTime; 
		this.endTime = endTime; 
		this.duration = duration; 
	}
	
	public Duration getTimeDuration() {
		return this.duration; 
	}
	
	public String getDuration() {
		if (duration == null)
			return null; 
		
		String durationString = "PT";
		durationString += String.format("%02dH", duration.getHours());
		durationString += String.format("%02dM", duration.getMinutes()); 
		durationString += String.format("%02dS", duration.getSeconds()); 
		
//		System.out.println("Duration: " + durationString); 
		return durationString; 
	}
	
	public void print() {
		System.out.println("Start Time: " + (startTime != null ? TimeFormatFactory.timeFormat.format(startTime.getTime()) : ""));
		System.out.println("End Time: " + (endTime != null ? TimeFormatFactory.timeFormat.format(endTime.getTime()) : ""));
//		System.out.println("Duration: " + duration.toString()); 
		System.out.println("Duration: " + getDuration()); 
	}
	
	public void toXML(Element timeRecordElement) {
		Element startTimeElement = timeRecordElement.addElement("project_start_time"); 
		startTimeElement.setText((startTime != null ? TimeFormatFactory.timeFormat.format(startTime.getTime()) : ""));
		Element endTimeElement = timeRecordElement.addElement("project_end_time"); 
		endTimeElement.setText((endTime != null ? TimeFormatFactory.timeFormat.format(endTime.getTime()) : ""));
		Element durationElement = timeRecordElement.addElement("project_total_time"); 
		durationElement.setText(getDuration()); 
	}
}
