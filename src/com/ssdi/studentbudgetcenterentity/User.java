package com.ssdi.studentbudgetcenterentity;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	private String username;
	private String password;
	private int passwordseed;
	private String email;
	private int emailcount;
	private String account;
	private ArrayList<BudgetItem> userbi;
	private ArrayList<Transaction> usertran;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPasswordseed() {
		return passwordseed;
	}
	public void setPasswordseed(int passwordseed) {
		this.passwordseed = passwordseed;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getEmailcount() {
		return emailcount;
	}
	public void setEmailcount(int emailcount) {
		this.emailcount = emailcount;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public ArrayList<BudgetItem> getUserbi() {
		return userbi;
	}
	public void setUserbi(ArrayList<BudgetItem> userbi) {
		this.userbi = userbi;
	}
	public ArrayList<Transaction> getUsertran() {
		return usertran;
	}
	public void setUsertran(ArrayList<Transaction> usertran) {
		this.usertran = usertran;
	}
	
	

}
