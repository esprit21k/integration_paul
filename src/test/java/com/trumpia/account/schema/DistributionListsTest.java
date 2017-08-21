package com.trumpia.account.schema;

import java.io.IOException;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.trumpia.trumpia.schema.DistributionList;

public class DistributionListsTest {
	DistributionList lists;
	private final static String URL = "http://api.trumpia.com/rest/v1/";
	private final static String FIELD = "/list";
	HashMap<String, String> headers = new HashMap<String, String>();
	
	@Before
	public void before() {
		lists = new DistributionList("594c862a4bfe227e3a537be359d3381b","paulkim", "http://api.trumpia.com");
		headers.put("Content-Type", "application/json");
		headers.put("X-Apikey", "594c862a4bfe227e3a537be359d3381b");
	}
	
	@Test
	public void test() throws IOException {
		System.out.println(lists.getDistributionLists().toString());
	}
	@After
	public void after() {
		
	}
	
//	private void deleteLists() throws IOException {
//		try {
//			ArrayList<String> idList = new ArrayList<String>(custom.listCustomID());
//			for (int i=0; i<idList.size(); i++) {
//				HttpRequest request = new HttpRequest.Builder()
//						.URL(URL+App.trumpiaUserID+FIELD+"/"+idList.get(i))
//						.headers(headers)
//						.build();
//				String msg = request.delete();
//				System.out.println("delete Custom Data Field "+(i+1)+"/"+idList.size()+": "+msg);
//			}
//		}
//		catch (NullPointerException e) {
//			System.out.println("Custom Data Field is empty");
//			return;
//		}
//	}
//
//	private String putLists(JSONObject customBody) throws IOException {
//		RequestBody body = RequestBody.create(MediaType.parse("application/json"), customBody.toString());
//		HttpRequest request = new HttpRequest.Builder()
//				.URL(URL+App.trumpiaUserID+FIELD)
//				.headers(headers)
//				.setRawBody(body)
//				.build();
//		String msg = request.put();
//		return msg;
//	}

}
