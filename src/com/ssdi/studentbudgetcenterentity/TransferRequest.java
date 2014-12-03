package com.ssdi.studentbudgetcenterentity;

public class TransferRequest {
	private User sentFrom;
	private User sentTo;
	private char status;
	private double transferAmt;
	private int RequestId;

	public int getRequestId() {
		return RequestId;
	}

	public void setRequestId(int requestId) {
		RequestId = requestId;
	}

	public User getSentFrom() {
		return sentFrom;
	}

	public void setSentFrom(User sentFrom) {
		this.sentFrom = sentFrom;
	}

	public User getSentTo() {
		return sentTo;
	}

	public void setSentTo(User sentTo) {
		this.sentTo = sentTo;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public double getTransferAmt() {
		return transferAmt;
	}

	public void setTransferAmt(double transferAmt) {
		this.transferAmt = transferAmt;
	}

}
