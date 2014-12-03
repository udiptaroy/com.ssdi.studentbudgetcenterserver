package com.ssdi.studentbudgetcenterTDGateway;

import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.ssdi.studentbudgetcenterDataStore.DBConnection;
import com.ssdi.studentbudgetcenterentity.FriendRequest;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class FriendRequestTableDataGateway {

	public String insertFriendRequest(FriendRequest incomingRequest) {
		boolean result = false;
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement preparedstatement = null, preparedstatement1 = null;
		try {
			preparedstatement = conn.prepareStatement("use studbudctr;");
			preparedstatement.execute();

			String sentby = incomingRequest.getSentBy().getUsername();
			String sentto = incomingRequest.getSentTo().getUsername();

			// String requestDate = new SimpleDateFormat("yyyyMMdd_HHmmss")
			// .format(Calendar.getInstance().getTime());

			Calendar calendar = Calendar.getInstance();
			java.sql.Date requestDate = new java.sql.Date(calendar.getTime()
					.getTime());

			String status = "N";
			preparedstatement1 = conn
					.prepareStatement("insert into friendrequest (Sentby,Sentto,RequestDate, Status)values ('"
							+ sentby
							+ "','"
							+ sentto
							+ "','"
							+ requestDate
							+ "','" + status + "');");

			preparedstatement1.execute();

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

	public FriendRequest getFriendRequest(int requestId) {
		FriendRequest request = new FriendRequest();
		boolean result = false;
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement preparedstatement = null, preparedstatement1 = null;
		try {
			preparedstatement = conn.prepareStatement("use studbudctr;");
			preparedstatement.execute();
			preparedstatement1 = conn
					.prepareStatement("select * from FriendRequest where FID='"
							+ requestId + "'");
			ResultSet rs = preparedstatement1.executeQuery();
			while (rs.next()) {
				request.setRequestId(Integer.parseInt(rs.getString("FID")));
				request.setCreateDate(rs.getString("RequestDate"));

				UserTableDataGateway userGateway = new UserTableDataGateway();
				request.setSentBy(userGateway.getUser(rs.getString("Sentby")));
				request.setSentTo(userGateway.getUser(rs.getString("Sentto")));
				request.setStatus(rs.getString("Status").charAt(0));
				result = true;
			}
			if (result) {

				return request;
			} else
				return null;
		} catch (Exception ex) {

			System.out.println(ex.getMessage());
			return null;
		}
	}

	public ArrayList<FriendRequest> getPendingFriendRequests(String username) {
		ArrayList<FriendRequest> FriendRequests = new ArrayList<FriendRequest>();
		boolean result = false;
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement preparedstatement = null, preparedstatement1 = null;
		try {
			preparedstatement = conn.prepareStatement("use studbudctr;");
			preparedstatement.execute();
			preparedstatement1 = conn
					.prepareStatement("select * from FriendRequest where Status='N' and Sentto='"
							+ username + "'");
			ResultSet rs = preparedstatement1.executeQuery();
			while (rs.next()) {
				FriendRequest currentRequest = new FriendRequest();

				currentRequest.setRequestId(Integer.parseInt(rs
						.getString("FID")));
				currentRequest.setCreateDate(rs.getString("RequestDate"));

				UserTableDataGateway userGateway = new UserTableDataGateway();
				currentRequest.setSentBy(userGateway.getUser(rs
						.getString("Sentby")));
				currentRequest.setSentTo(userGateway.getUser(rs
						.getString("Sentto")));
				currentRequest.setStatus(rs.getString("Status").charAt(0));
				FriendRequests.add(currentRequest);
				result = true;
			}
			if (result) {

				return FriendRequests;
			} else
				return null;
		} catch (Exception ex) {

			System.out.println(ex.getMessage());
			return null;
		}
	}

	public boolean ProcessFriendRequest(int requestId, Character newStatus) {

		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement preparedstatement = null, preparedstatement1 = null;
		try {
			preparedstatement = conn.prepareStatement("use studbudctr;");
			preparedstatement.execute();
			preparedstatement1 = conn
					.prepareStatement("update FriendRequest set Status='"
							+ newStatus + "'where FID='" + requestId + "'");
			preparedstatement1.executeUpdate();
			return true;
		} catch (Exception ex) {

			System.out.println(ex.getMessage());
			return false;
		}
	}

}
