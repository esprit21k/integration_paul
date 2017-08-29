package com.trumpia.model.transientModel;

import static com.trumpia.util.LogUtils.getLogger;
import static com.trumpia.util.StringUtils.textIsBlankOrNull;
public class LoginEntity {
	private String username, APIKey;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAPIKey() {
		return APIKey;
	}

	public void setAPIKey(String aPIKey) {
		APIKey = aPIKey;
	}
	
	public boolean isValid() {
		getLogger(this).error(this.username);
		getLogger(this).error(this.APIKey);
		return (!textIsBlankOrNull(this.username) && !textIsBlankOrNull(this.APIKey));
	}
}
