package com.ssdi.studentbudgetcenterTDGateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ssdi.studentbudgetcenterDataStore.*;

import com.ssdi.studentbudgetcenterentity.User;

public class UserTableDataGateway {

	
	public User getUser(String userId){
		User user=new User();
		boolean result=false;
		Connection conn=DBConnection.getInstance().getConnection();
		PreparedStatement preparedstatement=null,preparedstatement1=null;
		  try {
			  	preparedstatement=conn.prepareStatement("use studbudctr;");
				preparedstatement.execute();
				preparedstatement1=conn.prepareStatement("select * from user where username='"+userId+"'");
				ResultSet rs=preparedstatement1.executeQuery();
				while(rs.next()){
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					result=true;
				}
					if(result)
				return user;
					else
						return null;
		    }catch (Exception ex) {

		        System.out.println(ex.getMessage());
		        return null;
		          }
			}
	
	public String insertUser(User user){
		
		boolean result1=false, result2=false;
		Connection conn=DBConnection.getInstance().getConnection();
		PreparedStatement preparedstatement=null,preparedstatement1=null,preparedstatement2=null;
		  try {
			  	preparedstatement=conn.prepareStatement("use studbudctr;");
				preparedstatement.execute();
				preparedstatement1=conn.prepareStatement("insert into user values('"+user.getUsername()+"','"+user.getPassword()+"',"+user.getPasswordseed()+",'"+user.getEmail()+"',"+user.getEmailcount()+");");
				preparedstatement1.execute();
				result1=true;
				preparedstatement2=conn.prepareStatement("update BankAccount set username='"+user.getUsername()+"';");
				result2=preparedstatement2.execute();
				result2=true;
				if(result1== true && result2==true)
				return "SUCCESS";
					else
						return "ERROR";
		    }catch (Exception ex) {

		        System.out.println(ex.getMessage());
		        return ex.getLocalizedMessage();
		          }
	}
	
	public Boolean deleteUser(){
		return true;
	}
	
	public String updateUser(){
		return null;
	}
	
}
