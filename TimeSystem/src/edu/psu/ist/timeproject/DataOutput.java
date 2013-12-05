package edu.psu.ist.timeproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class DataOutput {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://joshku.no-ip.biz:3306/IST420"; 
	
	static final String INSERT_CLIENT_SQL = "insert ignore into clients (client_id, client_name) values (?,?)"; 
	static final String UPDATE_CLIENT_SQL = "update clients set client_name=? where client_id=?"; 
	static final String SELECT_CLIENT_SQL = "select client_name from clients where client_id=?"; 
	static final String INSERT_EMPLOY_SQL = "insert ignore into employees (employee_id, employee_name) values (?,?)"; 
	static final String INSERT_RECORD_SQL = "insert into time_reports (employee_id, client_id, project_id, project_status, date_created, project_start_time, project_end_time, project_duration) values (?,?,?,?,?,?,?,?)"; 
	static final String INSERT_FAILED_SQL = "insert into failed_records (employee_id, client_id, project_id, project_status, date_created, project_start_time, project_end_time, project_duration) values (?,?,?,?,?,?,?,?)";
	
	static final String GET_CLIENT_ID_SQL = "select client_id from clients where client_name=?";
	static final String GET_EMPLOY_ID_SQL = "select employee_id from employees where employee_name=?";
	static final String GET_CLIENT_NAME_SQL = "select client_name from clients where client_id=?"; 
	static final String GET_EMPLOY_NAME_SQL = "select employee_name from employees where employee_id=?";
	
	static final String USER = "digitaldreamteam"; 
	static final String PASS = "digitaldreamteam"; 
	
	public static void saveRecord(TimeReport report, String recordQuery) {
		Connection conn = null; 
		PreparedStatement clientInsert = null;
		PreparedStatement clientUpdate = null; 
		PreparedStatement clientSelect = null; 
		PreparedStatement employInsert = null; 
		PreparedStatement recordInsert = null; 
		ResultSet rs = null; 
		try {
			Class.forName(JDBC_DRIVER); 
			conn = DriverManager.getConnection(DB_URL, USER, PASS); 
			
			clientInsert = conn.prepareStatement(INSERT_CLIENT_SQL); 
			clientUpdate = conn.prepareStatement(UPDATE_CLIENT_SQL); 
			clientSelect = conn.prepareStatement(SELECT_CLIENT_SQL);
			employInsert = conn.prepareStatement(INSERT_EMPLOY_SQL);
			recordInsert = conn.prepareStatement(recordQuery); 
			
			if (report.getClientID() != null) {
				clientSelect.setInt(1, report.getClientID());
				rs = clientSelect.executeQuery(); 
				if (rs.next() == false || rs.getString(1) == "N/A" || rs.getString(1).isEmpty()) {
					clientInsert.setInt(1, report.getClientID());
					clientInsert.setString(2, report.getClientName());
					clientInsert.executeUpdate(); 
				}
				else if (!report.getClientName().isEmpty()) {
					clientUpdate.setString(1,  report.getClientName());
					clientUpdate.setInt(2, report.getClientID());
					clientUpdate.executeUpdate(); 
				}
			}

			if (report.getEmployeeID() != null) {
				employInsert.setInt(1, report.getEmployeeID());
				employInsert.setString(2, report.getEmployeeName()); 
				employInsert.executeUpdate(); 
			}

			if (report.getEmployeeID() != null)
				recordInsert.setInt(1, report.getEmployeeID());
			else
				recordInsert.setNull(1, java.sql.Types.INTEGER);

			if (report.getClientID() != null)
				recordInsert.setInt(2, report.getClientID());
			else
				recordInsert.setNull(2, java.sql.Types.INTEGER);

			if(report.getProjectID() != null)
				recordInsert.setInt(3, report.getProjectID());
			else
				recordInsert.setNull(3, java.sql.Types.INTEGER);

			recordInsert.setString(4, report.getProjectStatus().name());
			recordInsert.setDate(5, report.getDateCreated() != null ? new java.sql.Date(report.getDateCreated().getTimeInMillis()) : null);
			recordInsert.setTime(6, report.getTimeRecord().getStartTime() != null ? new Time(report.getTimeRecord().getStartTime().getTimeInMillis()) : null);
			recordInsert.setTime(7,  report.getTimeRecord().getEndTime() != null ? new Time(report.getTimeRecord().getEndTime().getTimeInMillis()) : null);
			recordInsert.setString(8, report.getTimeRecord().getDuration());

			recordInsert.executeUpdate(); 
		}
		catch(SQLException sqle) {sqle.printStackTrace();} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (conn != null) {
				try {
					conn.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (clientInsert != null) {
				try {
					clientInsert.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			
			if (clientUpdate != null) {
				try {
					clientUpdate.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			
			if (clientSelect != null) {
				try {
					clientSelect.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			
			if (employInsert != null) {
				try {
					employInsert.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			
			if (recordInsert != null) {
				try {
					recordInsert.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			
			
		}
	}
		
	public static String queryForStringValue(int id, String queryString) {
		Connection conn = null; 
		PreparedStatement query = null;
		ResultSet rs = null; 
		try {
			Class.forName(JDBC_DRIVER); 
			conn = DriverManager.getConnection(DB_URL, USER, PASS); 
			
			query = conn.prepareStatement(queryString); 
			query.setInt(1, id);
			rs = query.executeQuery(); 
			
			if (rs.next())
				return rs.getString(1); 
			else
				return null; 

		}
		catch(SQLException sqle) {sqle.printStackTrace();} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			
			if (query != null) {
				try {
					query.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}
		
		return null; 
	}
	
	public static Integer queryForIntValue(String name, String queryString) {
		Connection conn = null; 
		PreparedStatement query = null;
		ResultSet rs = null; 
		try {
			Class.forName(JDBC_DRIVER); 
			conn = DriverManager.getConnection(DB_URL, USER, PASS); 
			
			query = conn.prepareStatement(queryString); 
			query.setString(1, name);
			rs = query.executeQuery(); 
			
			if (rs.next())
				return rs.getInt(1); 
			else
				return null; 

		}
		catch(SQLException sqle) {sqle.printStackTrace();} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			
			if (query != null) {
				try {
					query.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}
		return null; 
	}
	
	
}
