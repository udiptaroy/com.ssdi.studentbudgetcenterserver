package com.ssdi.studentbudgetcentercontroller;

import java.io.IOException;
import java.util.ArrayList;

import com.ssdi.studentbudgetcenterTDGateway.FriendRequestTableDataGateway;
import com.ssdi.studentbudgetcenterTDGateway.TransferRequestTableDataGateway;
import com.ssdi.studentbudgetcenterTDGateway.UserTableDataGateway;
import com.ssdi.studentbudgetcenterentity.FriendRequest;
import com.ssdi.studentbudgetcenterentity.TransferRequest;

public class RequestController {

	public String createFriendRequest(String sentby, String sentto)
			throws IOException {
		FriendRequest incomingRequest = new FriendRequest();
		if (!(sentby.equals("") || sentto.equals(""))) {
			UserTableDataGateway userGateway = new UserTableDataGateway();
			incomingRequest.setSentBy(userGateway.getUser(sentby));
			incomingRequest.setSentTo(userGateway.getUser(sentto));
			FriendRequestTableDataGateway gateway = new FriendRequestTableDataGateway();
			String str = gateway.insertFriendRequest(incomingRequest);
			return str;
		} else {
			return "Field Missing";
		}
	}

	public ArrayList<FriendRequest> getFriendRequests(String UserName) {
		FriendRequestTableDataGateway gateway = new FriendRequestTableDataGateway();
		return gateway.getPendingFriendRequests(UserName);

	}

	public void ReplyToFriendRequest(int requestID, char newStatus) {
		FriendRequestTableDataGateway gateway = new FriendRequestTableDataGateway();
		gateway.ProcessFriendRequest(requestID, newStatus);
	}

	public String createTransferRequest(String sentby, String sentto,
			Double amount) {
		TransferRequest incomingRequest = new TransferRequest();
		if (!(sentby.equals("") || sentto.equals(""))) {
			UserTableDataGateway userGateway = new UserTableDataGateway();
			incomingRequest.setSentFrom((userGateway.getUser(sentby)));
			incomingRequest.setSentTo(userGateway.getUser(sentto));
			incomingRequest.setTransferAmt(amount);
			TransferRequestTableDataGateway gateway = new TransferRequestTableDataGateway();
			String str = gateway.insertTransferRequest(incomingRequest);
			return str;
		} else {
			return "Field Missing";
		}
	}

	public ArrayList<TransferRequest> getTransferRequests(String UserName) {
		TransferRequestTableDataGateway gateway = new TransferRequestTableDataGateway();
		return gateway.getPendingTransferRequests(UserName);
	}

	public void ReplyToTransferRequest(int requestID, char newStatus) {
		TransferRequestTableDataGateway gateway = new TransferRequestTableDataGateway();
		gateway.ProcessTransferRequest(requestID, newStatus);
	}

}
