package com.ssdi.studentbudgetcenterentity;

public class BudgetItem {
	private String category;
	private double budgetTarget;
	private String username;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public double getBudgetTarget() {
		return budgetTarget;
	}
	public void setBudgetTarget(double budgetTarget) {
		this.budgetTarget = budgetTarget;
	}
	

}
