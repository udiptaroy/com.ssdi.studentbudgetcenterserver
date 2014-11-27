package com.ssdi.studentbudgetcenterentity;

public class Transaction {
	private String budgetCategory;
	private String transactionDate;
	private double transactionAmt;
	private String transactionDesc;
	public String getBudgetCategory() {
		return budgetCategory;
	}
	public void setBudgetCategory(String budgetCategory) {
		this.budgetCategory = budgetCategory;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public double getTransactionAmt() {
		return transactionAmt;
	}
	public void setTransactionAmt(double transactionAmt) {
		this.transactionAmt = transactionAmt;
	}
	public String getTransactionDesc() {
		return transactionDesc;
	}
	public void setTransactionDesc(String transactionDesc) {
		this.transactionDesc = transactionDesc;
	}

}
