package com.trumpia.trumpia.inbound.model;

public class InboundInfo {
	private String subscriptionId;
	private String phoneNumber;
	private String keyword;
	private String dataCapture;
	
	public String getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getDataCapture() {
		return dataCapture;
	}
	public void setDataCapture(String dataCapture) {
		this.dataCapture = dataCapture;
	}
	
	@Override
	public String toString() {
		return "ParsedInbound [subscriptionId=" + subscriptionId + ", phoneNumber=" + phoneNumber + ", keyword="
				+ keyword + ", dataCapture=" + dataCapture + "]";
	}
}
