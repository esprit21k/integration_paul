package com.trumpia.account.schema;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trumpia.Main;
import com.trumpia.trumpia.data.TrumpiaAccountRepository;
import com.trumpia.trumpia.model.TrumpiaAccountEntity;
import com.trumpia.trumpia.schema.DistributionList;
import com.trumpia.util.Http.HttpRequest;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class DistributionListsTest {
	private DistributionList lists;
	private JSONArray distributionLists;
	private final static String URL = "http://api.trumpia.com/rest/v1/paulkim/list";
	HashMap<String, String> headers = new HashMap<String, String>();
	
	@Before
	public void before() throws IOException {
		lists = new DistributionList("594c862a4bfe227e3a537be359d3381b", "paulkim", "http://api.trumpia.com");
		headers.put("Content-Type", "application/json");
		headers.put("X-Apikey", "594c862a4bfe227e3a537be359d3381b");
	}
	
	@Test
	public void test() throws IOException {
		distributionLists = lists.getDistributionLists();
		List<String> name = new ArrayList<String>();
		for(int i=0; i<distributionLists.length(); i++) {
			name.add(distributionLists.getString(i));
		}
		System.out.println("Lists: "+name);
		assertTrue(name.contains("MyContacts"));
		System.out.println(distributionLists.toString());
	}
	@After
	public void after() throws IOException {
		deleteLists(distributionLists);
	}
	
	private void deleteLists(JSONArray distributionLists) throws IOException {
		try {
			for (int i=0; i<distributionLists.length(); i++) {
				HttpRequest request = new HttpRequest.Builder()
						.URL(URL+"/"+distributionLists.get(i))
						.headers(headers)
						.build();
				String msg = request.delete();
				System.out.println("delete Distribution List "+(i+1)+"/"+distributionLists.length()+": "+msg);
			}
		}
		catch (NullPointerException e) {
			System.out.println("Distribution List is empty");
			return;
		}
	}

//	private String putLists(String putBody) throws IOException {
//		RequestBody body = RequestBody.create(MediaType.parse("application/json"), putBody);
//		HttpRequest request = new HttpRequest.Builder()
//				.URL(URL)
//				.headers(headers)
//				.setRawBody(body)
//				.build();
//		String msg = request.put();
//		return msg;
//	}
	
	
//	private String makeBody() {
//		JSONObject body = new JSONObject();
//		body.put("list_name", "test");
//		body.put("display_name", "test");
//		body.put("frequency", "1");
//		body.put("description", "test");
//		System.out.println("requestbody: "+body.toString());
//		return body.toString();
//	}

}
