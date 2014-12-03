package com.ssdi.studentbudgetcentercontroller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.ssdi.studentbudgetcenterTDGateway.FriendRequestTableDataGateway;
import com.ssdi.studentbudgetcenterTDGateway.TransactionTableDataGateway;
import com.ssdi.studentbudgetcenterTDGateway.TransferRequestTableDataGateway;
import com.ssdi.studentbudgetcenterTDGateway.UserTableDataGateway;
import com.ssdi.studentbudgetcenterentity.FriendRequest;
import com.ssdi.studentbudgetcenterentity.Transaction;
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

	public boolean ReplyToTransferRequest(int requestID, char newStatus) {
		TransferRequestTableDataGateway gateway = new TransferRequestTableDataGateway();
		if (newStatus == 'A') {
			TransferRequest currentRequest = gateway
					.getTransferRequest(requestID);

			ArrayList<Transaction> transList = new ArrayList<Transaction>();
			TransactionTableDataGateway transactionGateway = new TransactionTableDataGateway();

			Transaction newTransaction = new Transaction();
			newTransaction.setBudgetCategory("Transfer");
			newTransaction.setTransactionAmt(currentRequest.getTransferAmt());

			java.util.Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());

			newTransaction.setTransactionDate(timestamp.toString());
			newTransaction.setTransactionDesc(currentRequest.getSentFrom()
					.getUsername());
			transList.add(newTransaction);
			transactionGateway.insertTransaction(currentRequest.getSentTo()
					.getUsername(), transList);

		}
		gateway.ProcessTransferRequest(requestID, newStatus);
		return true;
	}
}
