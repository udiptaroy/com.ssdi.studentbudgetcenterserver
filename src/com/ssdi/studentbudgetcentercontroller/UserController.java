package com.ssdi.studentbudgetcentercontroller;

import java.io.IOException;
import java.util.Map;

import com.ssdi.studentbudgetcenterTDGateway.UserTableDataGateway;
import com.ssdi.studentbudgetcenterentity.BankAccount;
import com.ssdi.studentbudgetcenterentity.User;

public class UserController {

	public String getUser(Map<String, String[]> map) throws IOException {
		String username = map.get("username")[0];
		User user = new User();
		
		if(!username.equalsIgnoreCase("")){
			UserTableDataGateway utg = new UserTableDataGateway();
			user = utg.getUser(username);
			if(user!= null && user.getPassword().equals(map.get("password")[0]))
				return "SUCCESS";
			else{
				return "NO user in system. Kindly register";
			}
		}else{
			return "no user selected.";
		}
	}
	public String createUser(Map<String, String[]> map) throws IOException {
		String username = map.get("username2")[0];
		String password = map.get("password2")[0];
		String email = map.get("email")[0];
		String account = map.get("bankaccount")[0];
		User user=new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setPasswordseed(0);
		user.setEmail(email);
		user.setAccount(account);
		user.setEmailcount(0);
		UserTableDataGateway utd=new UserTableDataGateway();
		String str=utd.insertUser(user);
			return str;
		}
}