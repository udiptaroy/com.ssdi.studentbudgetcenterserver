package com.ssdi.studentbudgetcenterDataStore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private  Connection connection;
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	private static DBConnection dbManager;
	
	private DBConnection() throws Exception{
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost/udipta","root","soumita123");
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(ClassNotFoundException ex){
		System.out.println(ex.getMessage());
		}
	}
	public static DBConnection getInstance(){
		try{
			if(dbManager==null){
				dbManager=new DBConnection();
				
			}
			}catch(Exception e){
				System.out.println("sfdhghm");
		}
			
		return dbManager;
	}

}
