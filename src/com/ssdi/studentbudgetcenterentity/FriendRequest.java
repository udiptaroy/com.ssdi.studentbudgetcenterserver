package com.ssdi.studentbudgetcenterentity;

public class FriendRequest {
	private User sentBy;
	private User sentTo;
	private char status;
	private String createDate;
	public User getSentBy() {
		return sentBy;
	}
	public void setSentBy(User sentBy) {
		this.sentBy = sentBy;
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
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	

}
