package com.ssdi.studentbudgetcenterTDGateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.ssdi.studentbudgetcenterDataStore.DBConnection;
import com.ssdi.studentbudgetcenterentity.BudgetItem;
import com.ssdi.studentbudgetcenterentity.User;

public class BudgetItemTableDataGateway {
	public ArrayList<BudgetItem> getBudgetItem(String username){
		ArrayList<BudgetItem> res=new ArrayList<BudgetItem>();
		boolean result=false;
		Connection conn=DBConnection.getInstance().getConnection();
		PreparedStatement preparedstatement=null,preparedstatement1=null;
		  try {
			  	preparedstatement=conn.prepareStatement("use studbudctr;");
				preparedstatement.execute();
				preparedstatement1=conn.prepareStatement("select * from BudgetItems where username='"+username+"'");
				ResultSet rs=preparedstatement1.executeQuery();
				while(rs.next()){
					BudgetItem bi=new BudgetItem();
					bi.setUsername("username");
					bi.setCategory("Category");
					bi.setBudgetTarget(3);
					res.add(bi);
					
					result=true;
				}
				
					if(result)
				return res;
					else
						return null;
		    }catch (Exception ex) {

		        System.out.println(ex.getMessage());
		        return null;
		          }
	}
public String insertBudgetItem(ArrayList<BudgetItem> listbuditem){
	boolean result=false;
	Connection conn=DBConnection.getInstance().getConnection();
	PreparedStatement preparedstatement=null,preparedstatement1=null;
	  try {
		  	preparedstatement=conn.prepareStatement("use studbudctr;");
			preparedstatement.execute();
			for(BudgetItem bi:listbuditem){
				String username=bi.getUsername();
				String category=bi.getCategory();
				Double target=bi.getBudgetTarget();
				preparedstatement1=conn.prepareStatement("insert into BudgetItems (Category,BudgetTarget,Username)values ('"+category+"',"+target+",'"+username+"');");
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
public String deleteBudgetItem(String username){
	boolean result=false;
	Connection conn=DBConnection.getInstance().getConnection();
	PreparedStatement preparedstatement=null,preparedstatement1=null;
	  try {
		  	preparedstatement=conn.prepareStatement("use studbudctr;");
			preparedstatement.execute();
			
				preparedstatement1=conn.prepareStatement("delete from BudgetItems where Username='"+username+"';");
				preparedstatement1.execute();
								
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

}

