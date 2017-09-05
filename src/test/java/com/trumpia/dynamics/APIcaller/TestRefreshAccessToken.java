package com.trumpia.dynamics.APIcaller;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trumpia.util.Http.HttpRequest;

public class TestRefreshAccessToken {
	String refreshToken = "AQABAAAAAAA9kTklhVy7SJTGAzR-p1Bcl8fxzcqzJ86soQzO6HFaKRFbWlxEziD0oFFt5me91HenV11FoohDAtoWAnEmEMy5xQq9ScNzzBzWG4_-squanBKAvYzAr3XssGGw73Ha8j93p2DV6uk0N-nta8a8H1yL1O66bR6V8jPlmUcCu3-PD7t4JLxi5Ml5yaRM5ZHbg6fbFQ3bx9wiPMzJSyW7BZr7PiG8ywIQ4XRTV5sCQSrGo5yFIobw16m7BXznKEgFmIAGqixZF0E9ElQ3Dg7YVfWBt7nX-jwVy7Lf2qJ2i4Cr_x1F67_Wc2jhEApZoduqbIRIZ6rQJBUpjbVW1yFTgdB6j6Sxd0qqHBbTGSE_2S5jQB5W3ejFEjZ2ZtliqnfzWkEsbcf_6zcnBldkziQI5DJBWisQyyuQ6QpPzZ6kxhyROLdPvVpTwjEbv_av7Hu1uGeYHKxpRbKXHB7UcHFXE8MSg_DFiuhNVaypjmYyg8piqX0HeBk2moaiKCApuY4Ngn9yox-oNpJDc6mb3Hc9ayTiZaaBVsbej5cndfndBivfWJ_tshnoOWRkaFugmW9JiYuAql-M4zgUZy3rCKkjM9eaNcK_-dg-X5KJyjIvQNTK35sgNF2Usn8Finsd8JqrDgiUpFyx8t7kEtZC4lUO3VkB-bnT1mN2zSID9T4Ji0wN2pJ1HzMJlXX7V9QhGE1NWA1Wm2r1uS14n6hKI1qhnOGfG6yJJVlp89BPpeXR5R9_SBjk5yw7FrSdUee4DInYdG2uwCrsrRFgeGGg8wuESr-vIAA";
	
//	@Test
//	public void test() throws IOException {
//		RefreshAccessToken refreshAccessToken = new RefreshAccessToken(refreshToken, "https://trumpia.crm.dynamics.com");
//		refreshAccessToken.updateToken();
//		assertNotNull(refreshAccessToken.accessToken);
//		System.out.println("refreshToken: "+refreshAccessToken.refreshToken);
//		System.out.println("accessToken: "+refreshAccessToken.accessToken);
//	}
//	
//	@Test
//	public void test2() {
//		ObjectMapper mapper = new ObjectMapper();
//		String mobileNumber = "101010";
//		ObjectNode lastObject = mapper.createObjectNode();
//		ObjectNode subscriptionInfo = mapper.createObjectNode();
//		ObjectNode mobileInfo = mapper.createObjectNode();
//		ArrayNode customDataInfo = mapper.createArrayNode();
//		customDataInfo.add("hi");
//		customDataInfo.add("hi");
//		customDataInfo.add("hi");
//		mobileInfo.put("number", mobileNumber);
//		mobileInfo.put("country_code", "1");
//		subscriptionInfo.set("mobile", mobileInfo);
//		subscriptionInfo.put("first_name", "firstName");
//		subscriptionInfo.set("customdata", customDataInfo);
//		lastObject.set("subscriptions", subscriptionInfo);
//		System.out.println(lastObject);
//	}
	
	@Test
	public void test3() throws IOException {
		HashMap<String, String> headers = new HashMap<String, String>();

		headers.put("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IkhIQnlLVS0wRHFBcU1aaDZaRlBkMlZXYU90ZyIsImtpZCI6IkhIQnlLVS0wRHFBcU1aaDZaRlBkMlZXYU90ZyJ9.eyJhdWQiOiJodHRwczovL3RydW1waWEuY3JtLmR5bmFtaWNzLmNvbSIsImlzcyI6Imh0dHBzOi8vc3RzLndpbmRvd3MubmV0Lzk3NjFlYTkwLTNlNTMtNGYzMS1hMTFiLWJmNmFiNDcyYzQxYy8iLCJpYXQiOjE1MDQzMDU3NDQsIm5iZiI6MTUwNDMwNTc0NCwiZXhwIjoxNTA0MzA5NjQ0LCJhY3IiOiIxIiwiYWlvIjoiQVNRQTIvOEVBQUFBUndVWTNyR2pDc2hGL1I3YXRsT1ZmZ0YrYStOL0lUVmlLL3EyMmRpZ3hUND0iLCJhbXIiOlsicHdkIl0sImFwcGlkIjoiMGUyNjExZDAtM2I5NC00NmUzLTk2NDgtYTY4NjhiMDYyYTliIiwiYXBwaWRhY3IiOiIxIiwiZmFtaWx5X25hbWUiOiJBbHZhcmFkbyIsImdpdmVuX25hbWUiOiJSdWR5IiwiaXBhZGRyIjoiMTc0LjM1LjEyNC4xMzIiLCJuYW1lIjoiUnVkeSBBbHZhcmFkbyIsIm9pZCI6IjM3MDQyOGMzLTFjNjEtNGIyNy1hYzQxLWQ4NDc2Mzc0YWU3MiIsInB1aWQiOiIxMDAzN0ZGRUExRTZEMTE0Iiwic2NwIjoidXNlcl9pbXBlcnNvbmF0aW9uIiwic3ViIjoiTGVneThQeXhPYzRFWTBobk83ZHZGNVd5R1RORGRMd2FCZzhGb0h2YXJqYyIsInRpZCI6Ijk3NjFlYTkwLTNlNTMtNGYzMS1hMTFiLWJmNmFiNDcyYzQxYyIsInVuaXF1ZV9uYW1lIjoiUnVkeUB0cnVtcGlhLm9ubWljcm9zb2Z0LmNvbSIsInVwbiI6IlJ1ZHlAdHJ1bXBpYS5vbm1pY3Jvc29mdC5jb20iLCJ2ZXIiOiIxLjAiLCJ3aWRzIjpbIjYyZTkwMzk0LTY5ZjUtNDIzNy05MTkwLTAxMjE3NzE0NWUxMCJdfQ.cWKR4M6FgK16426-n14W-XEN9wehExbw6YpZs73sVIIgwanLOU2NOGqJHJAKZfUjz4mJ5XNATWVCyzoRbKjnSPoQV0HJPqhMmB0-hmfHSxRrP3_Ekgbj92pyt4gzgs_bS9QRbiM-dCUH5dwsbq_A_vX-rb2Bk9PlNyKO7pWVdD8z2iXwanwBOkKSWg_jbB1ov3KL7iQD60X8WX5GglTyIY_YlnZpqVkkYn7lKoIyYFlTpWUH11_8G36M9pgYjGRSH2ZFx_DLr0VAebl48iXzOA__F02aIQk0neI4Cn9h57-WEiJ6cwqg1zqYm_OloDyxxd513-i8t9Mj951TohJfHw");
		headers.put("OData-MaxVersion", "4.0");
		headers.put("OData-Version", "4.0");

		HttpRequest request = new HttpRequest.Builder()
				.URL("https://trumpia.crm.dynamics.com/api/data/v8.2/$metadata#contact")
				.headers(headers)
				.build();
		String msg = request.get();
		System.out.println(msg);
	}
}
