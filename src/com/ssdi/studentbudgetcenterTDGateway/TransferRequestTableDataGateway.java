package com.ssdi.studentbudgetcenterTDGateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.ssdi.studentbudgetcenterDataStore.DBConnection;
import com.ssdi.studentbudgetcenterentity.TransferRequest;

public class TransferRequestTableDataGateway {

	public String insertTransferRequest(TransferRequest incomingRequest) {
		boolean result = false;
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement preparedstatement = null, preparedstatement1 = null;
		try {
			preparedstatement = conn.prepareStatement("use studbudctr;");
			preparedstatement.execute();

			String sentFrom = incomingRequest.getSentFrom().getUsername();
			String sentTo = incomingRequest.getSentTo().getUsername();
			double TransferAmount = incomingRequest.getTransferAmt();

			String status = "N";
			preparedstatement1 = conn
					.prepareStatement("insert into transferrequest (sentFrom,sentTo, amount, status)values ('"
							+ sentFrom
							+ "','"
							+ sentTo
							+ "','"
							+ TransferAmount + "','" + status + "');");

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

	public TransferRequest getTransferRequest(int requestId) {
		TransferRequest request = new TransferRequest();
		boolean result = false;
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement preparedstatement = null, preparedstatement1 = null;
		try {
			preparedstatement = conn.prepareStatement("use studbudctr;");
			preparedstatement.execute();
			preparedstatement1 = conn
					.prepareStatement("select * from transferrequest where transferID='"
							+ requestId + "'");
			ResultSet rs = preparedstatement1.executeQuery();
			while (rs.next()) {
				request.setRequestId(Integer.parseInt(rs
						.getString("transferID")));

				UserTableDataGateway userGateway = new UserTableDataGateway();
				request.setSentFrom(userGateway.getUser(rs
						.getString("sentFrom")));
				request.setSentTo(userGateway.getUser(rs.getString("sentTo")));
				request.setStatus(rs.getString("status").charAt(0));
				request.setTransferAmt(Double.parseDouble(rs
						.getString("amount")));
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

	public ArrayList<TransferRequest> getPendingTransferRequests(String username) {
		ArrayList<TransferRequest> TransferRequests = new ArrayList<TransferRequest>();
		boolean result = false;
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement preparedstatement = null, preparedstatement1 = null;
		try {
			preparedstatement = conn.prepareStatement("use studbudctr;");
			preparedstatement.execute();
			preparedstatement1 = conn
					.prepareStatement("select * from transferrequest where status='N' and sentTo='"
							+ username + "'");
			ResultSet rs = preparedstatement1.executeQuery();
			while (rs.next()) {
				TransferRequest currentRequest = new TransferRequest();

				currentRequest.setRequestId(Integer.parseInt(rs
						.getString("transferID")));

				UserTableDataGateway userGateway = new UserTableDataGateway();
				currentRequest.setSentFrom(userGateway.getUser(rs
						.getString("sentFrom")));
				currentRequest.setSentTo(userGateway.getUser(rs
						.getString("sentTo")));
				currentRequest.setStatus(rs.getString("status").charAt(0));

				currentRequest.setTransferAmt(Double.parseDouble(rs
						.getString("amount")));

				TransferRequests.add(currentRequest);
				result = true;
			}
			if (result) {

				return TransferRequests;
			} else
				return null;
		} catch (Exception ex) {

			System.out.println(ex.getMessage());
			return null;
		}
	}

	public boolean ProcessTransferRequest(int requestId, Character newStatus) {

		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement preparedstatement = null, preparedstatement1 = null;
		try {
			preparedstatement = conn.prepareStatement("use studbudctr;");
			preparedstatement.execute();
			preparedstatement1 = conn
					.prepareStatement("update TransferRequest set Status='"
							+ newStatus + "'where transferID='" + requestId
							+ "'");
			preparedstatement1.executeUpdate();
			return true;
		} catch (Exception ex) {

			System.out.println(ex.getMessage());
			return false;
		}
	}

}
