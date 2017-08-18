package com.trumpia.dynamics.APIcaller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import org.json.JSONObject;

import com.trumpia.util.Http.HttpRequest;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RefreshAccessToken {
	private static String REFRESH_TOKEN = "AQABAAAAAABnfiG-mA6NTae7CdWW7Qfd2cr918F76yI_lb2yH4UrcAZs61Uw9kFJrmEeBuqeCXnvrMC7tvF4BMMq8rMaT3sPeMkMUIdoJo8YRrOs8Hzk2fxn9OZfnfthUTX86hcspEkO4l2qwUTzUNh7nsNeyq-KqTrpnCysO1jl0MWx63ZUb5ji3U9CIIMsJxZwRTdocyRPUyte0A-K3hoNsCd7xv0mDoxOvdjMatn9ZszQYdRxzI4l86NEaroIKHqjrlha6OEs_z5c-sCpzsxBrxL_4foKkuciu2UHY4EN_IHTywzEidkpzdYOIXqJd4naRDBaEclpp9orhnX2aZPstzbbPK7PNZW5YA-WI6GPOO1wg8kf9JyUk19jecTBZNZSwIVClqOacDEnioZfooYVUwHAZRQFDh-OWNZAWuTC6UCaMoGddRMdm3HWnP3BivT_rDerz1rwu22HuMX0ihxjDs34mhLIm4fCwi8gTDSLm1RbcJh09eZ_hJ9v8S3rYGhpS5aq3Y6aE_DaGQS958X73srP0TKqku4XJutXXgyd-O7VemPFUQ0K0X_avmnHtFL4MOv9lOcmmHG5mKT3zfEWdlO4RMgEmwBvMB4niPU0W56BEHF9AG9dHWoBUNuBbRZaNL4fxShPolT38BG_giO9nhmiBLQl7d7Suheo9HcD7rTxXCu3e43qePTlIADD86Llw-WqCNkELaGkBIwe5Cbj9LqKKQFiCj0nzeaigicm_Wdijw7vQWTJxJMdRXMZdHDQVwKZWgEvCghNLyVuq2s0vCyuaqaVIAA";
	
	public static String updateAccessToken() throws IOException {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("content-type", "application/x-www-form-urlencoded");
		headers.put("OData-MaxVersion", "4.0");
		headers.put("OData-Version", "4.0");
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, "client_id=0e2611d0-3b94-46e3-9648-a6868b062a9b" + 
				"&refresh_token=" + REFRESH_TOKEN + 
				"&grant_type=refresh_token" + 
				"&resource=https://trumpia.crm.dynamics.com" + 
				"&client_secret=Bd3mlGtxbvSw8VSiit9u6j3Mkrkj8WPD5a7GfQrGce4=");
		HttpRequest request = new HttpRequest.Builder()
				.URL("https://login.microsoftonline.com/9761ea90-3e53-4f31-a11b-bf6ab472c41c/oauth2/token")
				.setRawBody(body)
				.build();
		String msg = request.post();
		System.out.println("msg: "+msg);
		JSONObject data = new JSONObject(msg);
		REFRESH_TOKEN = data.get("refresh_token").toString();
		
		return data.get("access_token").toString();
	}
}
