package com.ssdi.studentbudgetcentercontroller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;
import com.ssdi.studentbudgetcenterTDGateway.BudgetItemTableDataGateway;
import com.ssdi.studentbudgetcenterTDGateway.TransactionTableDataGateway;
import com.ssdi.studentbudgetcenterTDGateway.UserTableDataGateway;
import com.ssdi.studentbudgetcenterentity.BankAccount;
import com.ssdi.studentbudgetcenterentity.BudgetItem;
import com.ssdi.studentbudgetcenterentity.Transaction;
import com.ssdi.studentbudgetcenterentity.User;

public class UserController {

	public String getUser(Map<String, String[]> map) throws IOException {
		String username = map.get("username")[0];
		User user = new User();

		if (!username.equalsIgnoreCase("")) {
			UserTableDataGateway utg = new UserTableDataGateway();
			user = utg.getUser(username);
			if (user != null
					&& user.getPassword().equals(map.get("password")[0]))
				return "SUCCESS";
			else {
				return "NO user in system. Kindly register";
			}
		} else {
			return "no user selected.";
		}
	}

	public User getUser(String username) throws IOException {
		User user = new User();

		if (!username.equalsIgnoreCase("")) {
			UserTableDataGateway utg = new UserTableDataGateway();
			user = utg.getUser1(username);
			if (user != null)
				return user;
			else {
				return null;
			}
		} else {
			return null;
		}
	}

	public String createUser(Map<String, String[]> map) throws IOException {
		String username = map.get("username2")[0];
		String password = map.get("password2")[0];
		String email = map.get("email")[0];
		String account = map.get("bankaccount")[0];
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setPasswordseed(0);
		user.setEmail(email);
		user.setAccount(account);
		user.setEmailcount(0);
		UserTableDataGateway utd = new UserTableDataGateway();
		String str = utd.insertUser(user);
		return str;
	}

	public String createBudget(String username, Map<String, String[]> map)
			throws IOException {

		double food = Double.parseDouble(map.get("exp1")[0]);
		double education = Double.parseDouble(map.get("exp2")[0]);
		double transport = Double.parseDouble(map.get("exp3")[0]);
		double entertainment = Double.parseDouble(map.get("exp4")[0]);
		double misc = Double.parseDouble(map.get("exp5")[0]);
		ArrayList<BudgetItem> items = new ArrayList<BudgetItem>();
		BudgetItem bi1 = new BudgetItem();
		bi1.setUsername(username);
		bi1.setCategory("Food");
		bi1.setBudgetTarget(food);
		items.add(bi1);
		BudgetItem bi2 = new BudgetItem();
		bi2.setUsername(username);
		bi2.setCategory("Education");
		bi2.setBudgetTarget(education);
		items.add(bi2);
		BudgetItem bi3 = new BudgetItem();
		bi3.setUsername(username);
		bi3.setCategory("Transport");
		bi3.setBudgetTarget(transport);
		items.add(bi3);
		BudgetItem bi4 = new BudgetItem();
		bi4.setUsername(username);
		bi4.setCategory("Entertainment");
		bi4.setBudgetTarget(entertainment);
		items.add(bi4);
		BudgetItem bi5 = new BudgetItem();
		bi5.setUsername(username);
		bi5.setCategory("Miscellaneous");
		bi5.setBudgetTarget(misc);
		items.add(bi5);
		BudgetItemTableDataGateway bitd = new BudgetItemTableDataGateway();
		String str = bitd.insertBudgetItem(items);
		return str;

	}

	public String createTransaction(String username, Map<String, String[]> map)
			throws IOException {
		ArrayList<Transaction> tran = new ArrayList<Transaction>();

		for (int i = 1; i < 6; i++) {
			String desc1 = map.get("itm" + i)[0];
			String cat1 = map.get("cat" + i)[0];
			Double exp1 = Double.parseDouble(map.get("exp" + i)[0]);
			String dt1 = map.get("dt" + i)[0];
			if (!(desc1.equals("") || cat1.equals("") || dt1.equals(""))) {
				Transaction t1 = new Transaction();
				t1.setTransactionDesc(desc1);
				t1.setBudgetCategory(cat1);
				t1.setTransactionAmt(exp1);
				t1.setTransactionDate(dt1);
				tran.add(t1);
			}
		}

		TransactionTableDataGateway ttdg = new TransactionTableDataGateway();
		String str = ttdg.insertTransaction(username, tran);
		return str;
	}

	public String updateAccount(Map<String, String[]> map) throws IOException {
		String password = map.get("password")[0];
		String email = map.get("email")[0];
		String account = map.get("bankaccount")[0];
		User user1 = new User();
		user1.setPassword(password);
		user1.setPasswordseed(0);
		user1.setEmail(email);
		user1.setEmailcount(0);
		user1.setAccount(account);
		UserTableDataGateway utdg = new UserTableDataGateway();
		String str = utdg.updateUser(user1);
		return str;
	}

	public static String getUserNames() {
		UserTableDataGateway utg = new UserTableDataGateway();
		Gson gson = new Gson();
		String jsonString = gson.toJson(utg.getUserNames());
		return jsonString;
	}
}