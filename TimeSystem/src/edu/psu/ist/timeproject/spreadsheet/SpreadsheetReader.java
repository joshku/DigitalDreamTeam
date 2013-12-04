package edu.psu.ist.timeproject.spreadsheet;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import edu.psu.ist.timeproject.DataCleanser;
import edu.psu.ist.timeproject.Status;
import edu.psu.ist.timeproject.TimeReport;
import edu.psu.ist.timeproject.util.TimeFormatFactory;

public class SpreadsheetReader {
	
	int projectIDIndex; 
	int projectStatusIndex; 
	int clientIDIndex; 
	int clientNameIndex; 
	int startTimeIndex; 
	int endTimeIndex; 
	int durationIndex = -1;  
	int totalDurationIndex = -1;  
	int employeeIDIndex; 
	int employeeNameIndex; 
	int dateCreatedIndex; 
	
	
	private void resetIndices() {
		projectIDIndex           = -1; 
		projectStatusIndex       = -1; 
		clientIDIndex            = -1;
		clientNameIndex          = -1; 
		startTimeIndex           = -1; 
		endTimeIndex             = -1; 
		durationIndex            = -1; 
		totalDurationIndex       = -1; 
		employeeIDIndex          = -1; 
		employeeNameIndex        = -1; 
		dateCreatedIndex         = -1;
	}
	
	private void getCellLocations(Row row) {
		
		resetIndices();  
		
		Iterator<Cell> iter = row.cellIterator(); 
		
		while(iter.hasNext()) {
			Cell cell = (Cell)iter.next(); 
			
			String header = cell.getStringCellValue(); 
			
			if (header.contains("Date") && header.contains("Created"))
				dateCreatedIndex = cell.getColumnIndex(); 
			else if (header.contains("Project") && header.contains("ID"))
				projectIDIndex = cell.getColumnIndex(); 
			else if (header.contains("Employee") && header.contains("ID"))
				employeeIDIndex = cell.getColumnIndex();
			else if (header.contains("Employee") && header.contains("Name"))
				employeeNameIndex = cell.getColumnIndex(); 
			else if (header.contains("Status"))
				projectStatusIndex = cell.getColumnIndex(); 
			else if (header.contains("Start") && header.contains("Time"))
				startTimeIndex = cell.getColumnIndex(); 
			else if (header.contains("End") && header.contains("Time"))
				endTimeIndex = cell.getColumnIndex(); 
			else if (header.contains("Total") && header.contains("Time"))
				durationIndex = cell.getColumnIndex(); 
			else if (header.equals("Total") && !header.contains("Time"))
				totalDurationIndex = cell.getColumnIndex(); 
			else if (header.contains("Client") && header.contains("ID"))
				clientIDIndex = cell.getColumnIndex(); 
			else if ((header.contains("Client") && header.contains("Name"))
				 || (header.equals("Organization")))
				clientNameIndex = cell.getColumnIndex(); 
		}
		
	}
	private boolean isRowEmpty(Row row) {
	    for (int c = row.getFirstCellNum(); c <= row.getLastCellNum(); c++) {
	        Cell cell = row.getCell(c);
	        if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
	            return false;
	    }
	    return true;
	}
	
	private Integer getIntegerValue(Row row, int index) {
		Integer value = 0; 
		if (row.getCell(index).getCellType() == Cell.CELL_TYPE_NUMERIC)
			value = (int)row.getCell(index).getNumericCellValue(); 
		else {
			String str = row.getCell(index).getStringCellValue();
			try {
				value = Integer.parseInt(str); 
			}
			catch(NumberFormatException nfe) {
				value = null; 
			}
		}
		
		return value; 
	}
	

	
	public ArrayList<TimeReport> read(File file) {
		ArrayList<TimeReport> reports = new ArrayList<TimeReport>(); 
        try
        {
//            FileInputStream file = new FileInputStream(new File("C:\\Users\\Code\\Documents\\School\\IST 420\\FinalProject\\TimeSystem\\lib\\Access_Data_Time_Reports_IST_420-001_FA1.xls"));
 
        	FileInputStream fis = new FileInputStream(file); 
        	
            //Create Workbook instance holding reference to .xlsx file
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
 
            //Get first/desired sheet from the workbook
            HSSFSheet sheet = workbook.getSheetAt(0);
 
            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            
            while (rowIterator.hasNext()) 
            {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0) {
                	getCellLocations(row); 
                	continue; 
                }
                
                if(isRowEmpty(row))
                	break; 

				Integer projectID = getIntegerValue(row, projectIDIndex); 
                Status status = (row.getCell(projectStatusIndex).getStringCellValue().equalsIgnoreCase("Closed") ? Status.closed : Status.open);   // Status 
                
				Integer clientID = getIntegerValue(row, clientIDIndex); 
				String clientName = row.getCell(clientNameIndex).getStringCellValue(); 
                Calendar startTime = Calendar.getInstance();
                if (row.getCell(startTimeIndex).getCellType() != Cell.CELL_TYPE_BLANK)
                	startTime.setTime( row.getCell(startTimeIndex).getDateCellValue() );     // Start Time
                else
                	startTime = null; 
                Calendar endTime = Calendar.getInstance(); 
                if (row.getCell(endTimeIndex).getCellType() != Cell.CELL_TYPE_BLANK)
                	endTime.setTime( row.getCell(endTimeIndex).getDateCellValue() );     // End Time
                else 
                	endTime = null; 
                
				DatatypeFactory dtFactory = DatatypeFactory.newInstance();
				Duration duration = null;
				String durationStr = ""; 
				if (totalDurationIndex != -1) {
					if (row.getCell(totalDurationIndex).getCellType() == Cell.CELL_TYPE_NUMERIC)
						durationStr = TimeFormatFactory.timeFormat.format(row.getCell(totalDurationIndex).getDateCellValue());   // Duration  
					else
						durationStr = row.getCell(totalDurationIndex).getStringCellValue(); 
				}		
				else if (durationIndex != -1) {
					if (row.getCell(durationIndex).getCellType() != Cell.CELL_TYPE_NUMERIC) 
						durationStr = DataCleanser.parseDuration( row.getCell(durationIndex).getStringCellValue() );
					else
						durationStr = DataCleanser.parseDuration( TimeFormatFactory.timeFormat.format(row.getCell(durationIndex).getDateCellValue() )); 
				}

				if (!durationStr.isEmpty())
					duration = dtFactory.newDuration( durationStr ); 
				
				Integer employeeID = getIntegerValue(row, employeeIDIndex); 
								
                String employeeName = row.getCell(employeeNameIndex).getStringCellValue();   // Employee Name          
                
                Calendar dateCreated = Calendar.getInstance(); 
                if (row.getCell(dateCreatedIndex).getCellType() != Cell.CELL_TYPE_BLANK)
                	dateCreated.setTime( row.getCell(dateCreatedIndex).getDateCellValue() );     // Date Created    
                else
                	dateCreated = null; 

                TimeReport report = new TimeReport(projectID, status, clientID, clientName, startTime, endTime, duration, employeeID, employeeName, dateCreated); 
                reports.add(report); 
            }
            fis.close();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return reports; 
    }

}
