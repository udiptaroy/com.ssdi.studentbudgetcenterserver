package com.ssdi.studentbudgetcenterTDGateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.ssdi.studentbudgetcenterDataStore.DBConnection;
import com.ssdi.studentbudgetcenterentity.BudgetItem;
import com.ssdi.studentbudgetcenterentity.Transaction;

public class TransactionTableDataGateway {
	public Transaction getTransaction(String username){
		Transaction tran=new Transaction();
		return tran;
	}
	public String insertTransaction(String username,ArrayList<Transaction> tran){
		boolean result=false;
		Connection conn=DBConnection.getInstance().getConnection();
		PreparedStatement preparedstatement=null,preparedstatement1=null;
		  try {
			  	preparedstatement=conn.prepareStatement("use studbudctr;");
				preparedstatement.execute();
				for(Transaction tr:tran){
					String desc=tr.getTransactionDesc();
					String cat=tr.getBudgetCategory();
					Double amt=tr.getTransactionAmt();
					String dt=tr.getTransactionDate();
					preparedstatement1=conn.prepareStatement("insert into Transaction (BudgetCategory,transactionDate,transactionAmount,description,Username)values ('"+cat+"','"+dt+"',"+amt+",'"+desc+"','"+username+"');");
					preparedstatement1.execute();
				}
								
					result=true;
				
					if(result)
				return "SUCCESS";
					else
						return "ERROR";
		    }catch (Exception ex) {

		        System.out.println(ex.getMessage());
		        return "ERROR";
		          }

	}
	public String deleteTransaction(String username){
		return "SUCCESS";
	}

}
