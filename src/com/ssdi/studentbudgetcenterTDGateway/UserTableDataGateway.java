package com.ssdi.studentbudgetcenterTDGateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.ssdi.studentbudgetcenterDataStore.*;
import com.ssdi.studentbudgetcenterentity.User;

public class UserTableDataGateway {

	
	public User getUser(String userId){
		User user=new User();
		boolean result=false;
		Connection conn=DBConnection.getInstance().getConnection();
		PreparedStatement preparedstatement=null,preparedstatement1=null,preparedstatement2=null,preparedstatement3=null,preparedstatement4=null,
				preparedstatement5=null,preparedstatement6=null;
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
					if(result){
						preparedstatement2=conn.prepareStatement("select sum(transactionAmount) from Transaction where username='"+userId+"'");
						ResultSet rs1=preparedstatement2.executeQuery();
						preparedstatement3=conn.prepareStatement("select sum(BudgetTarget) from BudgetItems where username='"+userId+"'");
						ResultSet rs2=preparedstatement3.executeQuery();
						while(rs1.next() && rs2.next()){
							String mail="";
							Double ratio=(rs1.getDouble(1))/(rs2.getDouble(1));
							preparedstatement4=conn.prepareStatement("select email from user where username='"+userId+"'");
							ResultSet rs3=preparedstatement4.executeQuery();
							while(rs3.next()){
								mail=rs3.getString(1);
							}
							if(ratio>=0.75 && ratio<1){
								sendEmail("You have consumed 75% of your Budget",mail);
								preparedstatement5=conn.prepareStatement("update user set Emailcount=1 where username='"+userId+"'");
								preparedstatement5.execute();
							}
							else if(ratio>=1){
								sendEmail("You have consumed 100% of your Budget",mail);
								preparedstatement6=conn.prepareStatement("update user set Emailcount=2 where username='"+userId+"'");
								preparedstatement6.execute();
							}

						}
						
				return user;
					}
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
	public static void sendEmail(String txt, String mail){
		String to = mail;

	      // Sender's email ID needs to be mentioned
	      String from = "udiptaroy@gmail.com";
	      Properties properties = System.getProperties();

	      // Assuming you are sending email from localhost
	      properties.put("mail.smtp.starttls.enable", "true"); 
	      properties.put("mail.smtp.host", "smtp.gmail.com");
	      properties.put("mail.smtp.user", "udiptaroy@gmail.com"); // User name
	      properties.put("mail.smtp.password", "focus123#"); // password
	      properties.put("mail.smtp.port", "587");
	      properties.put("mail.smtp.auth", "true");

	      //
	    

	      // Setup mail server
	   

	      // Get the default Session object.
	      Session session = Session.getDefaultInstance(properties, 
	    		    new javax.mail.Authenticator(){
	    		        protected PasswordAuthentication getPasswordAuthentication() {
	    		            return new PasswordAuthentication(
	    		                "udiptaroy@gmail.com", "focus123#");// Specify the Username and the PassWord
	    		        }
	    		});

	      try{
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject("Alert Mail from Student Budget Center!");

	         // Now set the actual message"
	         
	         message.setText(txt);

	         // Send message
	         Transport.send(message);
	         System.out.println("Sent message successfully....");
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }

		
	}
	
	public Boolean deleteUser(){
		return true;
	}
	
	public String updateUser(User user1){
		boolean result1=false, result2=false;
		Connection conn=DBConnection.getInstance().getConnection();
		PreparedStatement preparedstatement=null,preparedstatement1=null,preparedstatement2=null;
		  try {
			  	preparedstatement=conn.prepareStatement("use studbudctr;");
				preparedstatement.execute();
				preparedstatement1=conn.prepareStatement("update user set Password='"+user1.getPassword()+"', PasswordSeed="+user1.getPasswordseed()+", Email='"+user1.getEmail()+"', Emailcount="+user1.getEmailcount()+" where username='"+user1.getUsername()+"';");
				preparedstatement1.execute();
				result1=true;
				preparedstatement2=conn.prepareStatement("update BankAccount set AccountNumber='"+user1.getAccount()+"' where username='"+user1.getUsername()+"';");
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
	public User getUser1(String userId){
		User user=new User();
		boolean result=false;
		Connection conn=DBConnection.getInstance().getConnection();
		PreparedStatement preparedstatement=null,preparedstatement1=null,preparedstatement2=null;
		  try {
			  	preparedstatement=conn.prepareStatement("use studbudctr;");
				preparedstatement.execute();
				preparedstatement1=conn.prepareStatement("select * from user where username='"+userId+"'");
				ResultSet rs=preparedstatement1.executeQuery();
				preparedstatement2=conn.prepareStatement("select * from BankAccount where username='"+userId+"'");
				ResultSet rs1=preparedstatement2.executeQuery();
				while(rs.next() && rs1.next()){
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setEmail(rs.getString("email"));
					user.setAccount(rs1.getString("AccountNumber"));
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

	
}
