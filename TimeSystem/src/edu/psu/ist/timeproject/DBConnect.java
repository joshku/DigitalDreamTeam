package edu.psu.ist.timeproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

public class DBConnect {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/timesystem"; 
	
	static final String INSERT_CLIENT_SQL = "insert ignore into clients (client_id, client_name) values (?,?)"; 
	static final String INSERT_EMPLOY_SQL = "insert ignore into employees (employee_id, employee_name) values (?,?)"; 
	static final String INSERT_RECORD_SQL = "insert into time_reports (project_id, project_status, client_id, project_start_time, project_end_time, project_duration, employee_id, date_created) values (?,?,?,?,?,?,?,?)"; 
	
	static final String USER = "root"; 
	static final String PASS = "password"; 
	
	public static void saveRecords(ArrayList<TimeReport> reports) {
		Connection conn = null; 
		PreparedStatement clientInsert = null;
		PreparedStatement employInsert = null; 
		PreparedStatement recordInsert = null; 
		
		try {
			Class.forName(JDBC_DRIVER); 
			conn = DriverManager.getConnection(DB_URL, USER, PASS); 
			
			clientInsert = conn.prepareStatement(INSERT_CLIENT_SQL); 
			employInsert = conn.prepareStatement(INSERT_EMPLOY_SQL);
			recordInsert = conn.prepareStatement(INSERT_RECORD_SQL); 
			
			for (TimeReport report : reports) {
				clientInsert.setInt(1, report.getClientID());
				clientInsert.setString(2, "slfj");
				clientInsert.executeUpdate(); 
				
				employInsert.setInt(1, report.getEmployeeID());
				employInsert.setString(2, report.getEmployeeName()); 
				employInsert.executeUpdate(); 
				
				recordInsert.setInt(1, report.getProjectID());
				recordInsert.setString(2, report.getProjectStatus().name());
				recordInsert.setInt(3, report.getClientID());
				recordInsert.setTime(4, report.getTimeRecord().getStartTime() != null ? new Time(report.getTimeRecord().getStartTime().getTimeInMillis()) : null);
				recordInsert.setTime(5,  report.getTimeRecord().getEndTime() != null ? new Time(report.getTimeRecord().getEndTime().getTimeInMillis()) : null);
				recordInsert.setString(6, report.getTimeRecord().getDuration());
				recordInsert.setInt(7, report.getEmployeeID());
				recordInsert.setDate(8, new java.sql.Date(report.getDateCreated().getTimeInMillis()));
				recordInsert.executeUpdate(); 
			}
		}
		catch(SQLException sqle) {} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
