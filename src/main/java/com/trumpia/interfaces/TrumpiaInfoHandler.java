package com.trumpia.interfaces;

public interface TrumpiaInfoHandler {
	
	public default void checkKeyword(String keyword) {
		switch(keyword) {
		case "" : putRequest();
		case "REPLY" : return;
		case "STOP ALL" : deleteRequest();
		default : updateRequest();
		}
	}
	public void putRequest();
	public void updateRequest();
	public void deleteRequest();
}
