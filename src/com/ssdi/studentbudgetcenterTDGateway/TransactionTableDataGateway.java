package com.ssdi.studentbudgetcenterTDGateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.ssdi.studentbudgetcenterDataStore.DBConnection;
import com.ssdi.studentbudgetcenterentity.BudgetItem;
import com.ssdi.studentbudgetcenterentity.CompareObject;
import com.ssdi.studentbudgetcenterentity.Transaction;

public class TransactionTableDataGateway {
	public ArrayList<Transaction> getTransaction(String username) {
		ArrayList<Transaction> tran = new ArrayList<Transaction>();
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement preparedstatement = null, preparedstatement1 = null;
		try {
			preparedstatement = conn.prepareStatement("use studbudctr;");
			preparedstatement.execute();

			preparedstatement1 = conn
					.prepareStatement("select BudgetCategory,transactionDate,transactionAmount,description from Transaction where username='"
							+ username + "';");
			ResultSet rs = preparedstatement1.executeQuery();
			while (rs.next()) {
				Transaction tr = new Transaction();
				tr.setBudgetCategory(rs.getString(1));
				tr.setTransactionDate(rs.getString(2));
				tr.setTransactionAmt(rs.getDouble(3));
				tr.setTransactionDesc(rs.getString(4));
				tran.add(tr);
			}

			return tran;
		} catch (Exception ex) {

			System.out.println(ex.getMessage());
			return null;
		}

	}

	public String insertTransaction(String username, ArrayList<Transaction> tran) {
		boolean result = false;
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement preparedstatement = null, preparedstatement1 = null;
		try {
			preparedstatement = conn.prepareStatement("use studbudctr;");
			preparedstatement.execute();
			for (Transaction tr : tran) {
				String desc = tr.getTransactionDesc();
				String cat = tr.getBudgetCategory();
				Double amt = tr.getTransactionAmt();
				String dt = tr.getTransactionDate();
				preparedstatement1 = conn
						.prepareStatement("insert into Transaction (BudgetCategory,transactionDate,transactionAmount,description,Username)values ('"
								+ cat
								+ "','"
								+ dt
								+ "',"
								+ amt
								+ ",'"
								+ desc
								+ "','" + username + "');");
				preparedstatement1.execute();
			}

			result = true;

			if (result)
				return "SUCCESS";
			else
				return "ERROR";
		} catch (Exception ex) {

			System.out.println(ex.getMessage());
			return "ERROR";
		}

	}

	public ArrayList<CompareObject> getOthersTotal(String username) {
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement preparedstatement = null, preparedstatement1 = null, preparedstatement2 = null;
		double count = 0;
		ArrayList<CompareObject> colist = new ArrayList<CompareObject>();
		try {
			preparedstatement = conn.prepareStatement("use studbudctr;");
			preparedstatement.execute();

			preparedstatement1 = conn
					.prepareStatement("select BudgetCategory,sum(transactionAmount) from Transaction  where username<>'"
							+ username + "' group by BudgetCategory;");
			ResultSet rs = preparedstatement1.executeQuery();
			preparedstatement2 = conn
					.prepareStatement("select count(distinct(username)) from Transaction where username<>'"
							+ username + "';");
			ResultSet rs1 = preparedstatement2.executeQuery();
			while (rs1.next()) {
				count = rs1.getInt(1);
			}

			while (rs.next()) {
				CompareObject co = new CompareObject();
				co.setBudgetCategory(rs.getString(1));
				co.setAmount((rs.getDouble(2)) / (double) (count));
				colist.add(co);

			}

			return colist;
		} catch (Exception ex) {

			System.out.println(ex.getMessage());
			return null;
		}

	}

	public ArrayList<CompareObject> getOwnTotal(String username) {
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement preparedstatement = null, preparedstatement1 = null;

		ArrayList<CompareObject> colist = new ArrayList<CompareObject>();
		try {
			preparedstatement = conn.prepareStatement("use studbudctr;");
			preparedstatement.execute();

			preparedstatement1 = conn
					.prepareStatement("select BudgetCategory,sum(transactionAmount) from Transaction  where username='"
							+ username + "' group by BudgetCategory;");
			ResultSet rs = preparedstatement1.executeQuery();

			while (rs.next()) {
				CompareObject co = new CompareObject();
				co.setBudgetCategory(rs.getString(1));
				co.setAmount(rs.getDouble(2));
				colist.add(co);

			}

			return colist;
		} catch (Exception ex) {

			System.out.println(ex.getMessage());
			return null;
		}

	}

	public String deleteTransaction(String username) {
		return "SUCCESS";
	}

}
