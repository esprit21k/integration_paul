package com.trumpia.dynamics.APIcaller;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONObject;

import com.trumpia.util.Http.HttpRequest;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RefreshAccessToken {
	public String accessToken;
	public String refreshToken;
	public String resourseURL;
	
	public RefreshAccessToken(String refreshToken, String resourseURL) {
		this.refreshToken = refreshToken;
		this.resourseURL = resourseURL;
	}
		
	public void updateToken() throws IOException {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("content-type", "application/x-www-form-urlencoded");
		headers.put("OData-MaxVersion", "4.0");
		headers.put("OData-Version", "4.0");
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, "client_id=0e2611d0-3b94-46e3-9648-a6868b062a9b" + 
				"&refresh_token=" + refreshToken + 
				"&grant_type=refresh_token" + 
				"&resource=" + resourseURL + 
				"&client_secret=Bd3mlGtxbvSw8VSiit9u6j3Mkrkj8WPD5a7GfQrGce4=");
		HttpRequest request = new HttpRequest.Builder()
				.URL("https://login.microsoftonline.com/9761ea90-3e53-4f31-a11b-bf6ab472c41c/oauth2/token")
				.setRawBody(body)
				.build();
		String msg = request.post();
//		System.out.println("msg: "+msg);
		JSONObject responseData = new JSONObject(msg);
		this.accessToken = responseData.get("access_token").toString();
		this.refreshToken = responseData.get("refresh_token").toString();
		System.out.println(accessToken);
	}
}
