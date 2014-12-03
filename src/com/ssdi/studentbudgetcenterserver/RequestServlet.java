package com.ssdi.studentbudgetcenterserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

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

import com.google.gson.Gson;
import com.ssdi.studentbudgetcenterTDGateway.UserTableDataGateway;
import com.ssdi.studentbudgetcentercontroller.RequestController;
import com.ssdi.studentbudgetcentercontroller.UserController;
import com.ssdi.studentbudgetcenterentity.FriendRequest;
import com.ssdi.studentbudgetcenterentity.TransferRequest;

@Path("/requestcontroller")
public class RequestServlet {

	Map<String, String[]> params;

	@GET
	@Produces("application/jsonp")
	@Consumes(MediaType.APPLICATION_JSON)
	public String ProcessRequestsGet(
			@Context HttpServletResponse servletResponse,
			@Context HttpServletRequest servletRequest) throws IOException {

		HttpSession session = servletRequest.getSession();
		String username = (String) session.getAttribute("user");

		params = servletRequest.getParameterMap();
		String methodName = null;
		if (params.containsKey("methodId")) {
			methodName = params.get("methodId")[0];
		}

		String jsonString = null;
		if (methodName.equalsIgnoreCase("getFriendRequests")) {
			ArrayList<FriendRequest> currentFriendRequests = new ArrayList<FriendRequest>();
			RequestController RequestCon = new RequestController();
			currentFriendRequests = RequestCon.getFriendRequests(username);
			Gson gson = new Gson();
			jsonString = gson.toJson(currentFriendRequests);
		} else if (methodName.equalsIgnoreCase("getTransferRequests")) {
			ArrayList<TransferRequest> currentTransferRequests = new ArrayList<TransferRequest>();
			RequestController RequestCon = new RequestController();
			currentTransferRequests = RequestCon.getTransferRequests(username);
			Gson gson = new Gson();
			jsonString = gson.toJson(currentTransferRequests);
		} else if (methodName.equalsIgnoreCase("getUserNames")) {
			jsonString = UserController.getUserNames();
		}

		return jsonString;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean ProcessRequestsPost(
			@Context HttpServletResponse servletResponse,
			@Context HttpServletRequest servletRequest) throws IOException {

		HttpSession session = servletRequest.getSession();
		String username = (String) session.getAttribute("user");

		params = servletRequest.getParameterMap();
		String methodName = null;
		if (params.containsKey("methodId")) {
			methodName = params.get("methodId")[0];
		} else {
			methodName = params.keySet().iterator().next();
		}

		if (methodName.equalsIgnoreCase("acceptFriendRequest")) {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					servletRequest.getInputStream()));
			String json = "";
			if (br != null) {
				json = br.readLine();
			}

			int requestID = Integer
					.parseInt(json.substring(json.indexOf('=') + 1));

			RequestController RequestCon = new RequestController();
			RequestCon.ReplyToFriendRequest(requestID, 'A');
		} else if (methodName.equalsIgnoreCase("ignoreFriendRequest")) {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					servletRequest.getInputStream()));
			String json = "";
			if (br != null) {
				json = br.readLine();
			}

			int requestID = Integer
					.parseInt(json.substring(json.indexOf('=') + 1));

			RequestController RequestCon = new RequestController();
			RequestCon.ReplyToFriendRequest(requestID, 'I');
		} else if (methodName.equalsIgnoreCase("createFriendRequest")) {
			RequestController RequestCon = new RequestController();
			String sendTo = "";
			if (params.containsKey("sendto")) {
				sendTo = params.get("sendto")[0];
			}

			RequestCon.createFriendRequest(username, sendTo);
		} else if (methodName.equalsIgnoreCase("acceptTransferRequest")) {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					servletRequest.getInputStream()));
			String json = "";
			if (br != null) {
				json = br.readLine();
			}

			int requestID = Integer
					.parseInt(json.substring(json.indexOf('=') + 1));

			RequestController RequestCon = new RequestController();
			RequestCon.ReplyToTransferRequest(requestID, 'A');
		} else if (methodName.equalsIgnoreCase("ignoreTransferRequest")) {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					servletRequest.getInputStream()));
			String json = "";
			if (br != null) {
				json = br.readLine();
			}

			int requestID = Integer
					.parseInt(json.substring(json.indexOf('=') + 1));

			RequestController RequestCon = new RequestController();
			RequestCon.ReplyToTransferRequest(requestID, 'I');
		} else if (methodName.equalsIgnoreCase("createTransferRequest")) {
			RequestController RequestCon = new RequestController();
			String sendTo = "";
			if (params.containsKey("sendto")) {
				sendTo = params.get("sendto")[0];
			} else {
				return false;
			}

			Double amount = null;
			if (params.containsKey("amount")) {
				try {
					amount = Double.parseDouble(params.get("amount")[0]);
				} catch (NumberFormatException e) {
					return false;
				}
			} else {
				return false;
			}

			RequestCon.createTransferRequest(username, sendTo, amount);
		}
		return true;

	}
}