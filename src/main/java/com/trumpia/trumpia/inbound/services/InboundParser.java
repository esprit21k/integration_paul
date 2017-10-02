package com.trumpia.trumpia.inbound.services;

import com.trumpia.trumpia.inbound.model.InboundInfo;
import com.trumpia.util.XMLUtils;

public class InboundParser {
	public InboundInfo inboundInfo = new InboundInfo(); 
	
	public InboundParser(String inboundMsg) throws Exception {
		parsing(inboundMsg);
	}
	
	private void parsing(String inboundMsg) throws Exception {
		inboundInfo.setSubscriptionId(XMLUtils.getValueFromExpression(inboundMsg, "/TRUMPIA/SUBSCRIPTION_UID"));
		inboundInfo.setPhoneNumber(XMLUtils.getValueFromExpression(inboundMsg, "/TRUMPIA/PHONENUMBER"));
		inboundInfo.setKeyword((XMLUtils.getValueFromExpression(inboundMsg, "/TRUMPIA/KEYWORD")));
		inboundInfo.setDataCapture((XMLUtils.getValueFromExpression(inboundMsg, "/TRUMPIA/DATA_CAPTURE")));
	}
	
}
