package com.ssdi.studentbudgetcenterserver;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.ssdi.studentbudgetcentercontroller.ReportController;
import com.ssdi.studentbudgetcentercontroller.UserController;
import com.ssdi.studentbudgetcenterentity.User;

@Path("/usercontroller")
public class UserServlet {
	HttpSession session;
	@POST
	@Produces("application/jsonp")
	@Consumes(MediaType.APPLICATION_JSON)
	public String uiFormHandling(@Context HttpServletResponse servletResponse,
			@Context HttpServletRequest servletRequest) throws IOException {
		String methodName=servletRequest.getParameter("methodId");
		String str = "";
		
		if(methodName.equalsIgnoreCase("login")){
			UserController userCon = new UserController();
			str = userCon.getUser(servletRequest.getParameterMap());
			if(str.equals("SUCCESS")){
				session=servletRequest.getSession();
				session.setAttribute("user", servletRequest.getParameter("username"));
			}
		}
		else if(methodName.equalsIgnoreCase("register")){
			UserController userCon = new UserController();
			str = userCon.createUser(servletRequest.getParameterMap());
			if(str.equals("SUCCESS")){
				session=servletRequest.getSession();
				session.setAttribute("user",  servletRequest.getParameter("username2"));
			}
		}
		else if(methodName.equalsIgnoreCase("setbudgoals")){
			session=servletRequest.getSession();
			String username=(String)session.getAttribute("user");
			UserController userCon = new UserController();
		str = userCon.createBudget(username,servletRequest.getParameterMap());
		}
		else if(methodName.equalsIgnoreCase("transactions")){
			session=servletRequest.getSession();
			String username=(String)session.getAttribute("user");
			UserController userCon = new UserController();
		str = userCon.createTransaction(username,servletRequest.getParameterMap());
		}
		else if(methodName.equalsIgnoreCase("historicalreports")){
			session=servletRequest.getSession();
			String username=(String)session.getAttribute("user");
			ReportController rc=new ReportController();
		str=rc.generateBudgetReport(username);
		}
		else if(methodName.equalsIgnoreCase("comparereports")){
			session=servletRequest.getSession();
			String username=(String)session.getAttribute("user");
			ReportController rc=new ReportController();
			str=rc.compareBudgetReport(username);
			}
		else if(methodName.equalsIgnoreCase("updateAccount")){
			session=servletRequest.getSession();
			String username=(String)session.getAttribute("user");
			UserController userCon = new UserController();
		//str = userCon.updateAccount(username,servletRequest.getParameterMap());
		}
				
		
		return createJsonString(str);
	}

	@GET
	@Produces("application/jsonp")
	/*@Consumes(MediaType.APPLICATION_JSON)*/
	public void getUsername(@Context HttpServletResponse servletResponse,
			@Context HttpServletRequest servletRequest) throws IOException  {
		String methodName=servletRequest.getParameter("methodID");
		if(methodName.equalsIgnoreCase("getUserData")){
			session=servletRequest.getSession();
			String username=(String)session.getAttribute("user");
			UserController userCon = new UserController();
			User s = userCon.getUser(username);
			 servletResponse.getWriter().write(new Gson().toJson(s));
				System.out.println("str is = "+s);
				System.out.println("response created");
		}
		else if(methodName.equalsIgnoreCase("historicalreports")){
			session=servletRequest.getSession();
		String username=(String)session.getAttribute("user");
			//String username = (String) ((Object[])session.getAttribute("user"))[0];
			ReportController rc=new ReportController();
		String res=rc.generateBudgetReport(username);
		 servletResponse.getWriter().write(new Gson().toJson(res));
		 System.out.println("str is = "+res);
			System.out.println("response created");
		}
		
	}
	private String createJsonString(String str) {
		Gson gson = new Gson(); 
		String jsonString = gson.toJson(str);
		return jsonString;
	}
}