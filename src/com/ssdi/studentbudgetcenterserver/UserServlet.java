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
				HttpSession session=servletRequest.getSession();
				session.setAttribute("user",  servletRequest.getParameterMap().get("username")[0]);
			}
		}
		else if(methodName.equalsIgnoreCase("register")){
			UserController userCon = new UserController();
			str = userCon.createUser(servletRequest.getParameterMap());
			if(str.equals("SUCCESS")){
				HttpSession session=servletRequest.getSession();
				session.setAttribute("user",  servletRequest.getParameterMap().get("username2")[0]);
			}
		}
		else if(methodName.equalsIgnoreCase("setbudgoals")){
			HttpSession session=servletRequest.getSession();
			String username=(String)session.getAttribute("user");
			UserController userCon = new UserController();
		str = userCon.createBudget(username,servletRequest.getParameterMap());
		}
		else if(methodName.equalsIgnoreCase("transactions")){
			HttpSession session=servletRequest.getSession();
			String username=(String)session.getAttribute("user");
			UserController userCon = new UserController();
		str = userCon.createTransaction(username,servletRequest.getParameterMap());
		}
		else if(methodName.equalsIgnoreCase("historicalreports")){
			HttpSession session=servletRequest.getSession();
			String username=(String)session.getAttribute("user");
			ReportController rc=new ReportController();
		str=rc.generateBudgetReport(username);
		}
		else if(methodName.equalsIgnoreCase("comparereports")){
			HttpSession session=servletRequest.getSession();
			String username=(String)session.getAttribute("user");
			ReportController rc=new ReportController();
			str=rc.compareBudgetReport(username);
			}
		else if(methodName.equalsIgnoreCase("updateAccount")){
			HttpSession session=servletRequest.getSession();
			String username=(String)session.getAttribute("user");
			UserController userCon = new UserController();
		str = userCon.createTransaction(username,servletRequest.getParameterMap());
		}
				
		
		return createJsonString(str);
	}

	@GET
	@Produces("application/jsonp")
	@Consumes(MediaType.APPLICATION_JSON)
	public void getUsername(@Context HttpServletResponse servletResponse,
			@Context HttpServletRequest servletRequest) throws IOException  {
		String methodName=servletRequest.getParameter("methodName");
		if(methodName.equalsIgnoreCase("getUserData")){
			HttpSession session=servletRequest.getSession();
			String username=(String)session.getAttribute("user");
			UserController userCon = new UserController();
			User s = userCon.getUser(username);
			 servletResponse.getWriter().write(new Gson().toJson(s));
				System.out.println("str is = "+s);
				System.out.println("response created");
		}
		
	}
	private String createJsonString(String str) {
		Gson gson = new Gson(); 
		String jsonString = gson.toJson(str);
		return jsonString;
	}
}