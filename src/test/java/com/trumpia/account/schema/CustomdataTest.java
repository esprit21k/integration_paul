package com.trumpia.account.schema;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
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
import com.trumpia.trumpia.schema.api.CustomDataCall;
import com.trumpia.trumpia.schema.model.CustomData;
import com.trumpia.util.Http.HttpRequest;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CustomdataTest {
	CustomData custom;
	private final static String URL = "http://api.trumpia.com/rest/v1/";
	private final static String FIELD = "/customdata";
	private int inputType;
	private int required;
	private String name;
	HashMap<String, String> headers = new HashMap<String, String>();
	JSONObject options = new JSONObject();
	JSONObject customBody = new JSONObject();
	JSONArray optionsArray = new JSONArray();

	@Before 
	public void before() {
		custom = new CustomData("594c862a4bfe227e3a537be359d3381b", "paulkim", "http://api.trumpia.com");
		headers.put("content-type", "application/json");
		headers.put("x-apikey", "594c862a4bfe227e3a537be359d3381b");
	}

	@Test
	public void testTextField() throws IOException, JSONException {
		required = 2;
		name = "textfield";
		options = makeTextField(20, 200);
		testMethod();
		assertEquals(custom.getCustomdata().toString(), "[{\"name\":\""+name+"\",\"customdata_id\":"+custom.customDataCall.customID+",\"info\":{\"options\":"+ options +",\"input_type\":\""+custom.customDataCall.inputTypeMatch(inputType)+"\",\"required\":\""+custom.customDataCall.requiredMatch(required)+"\"}}]");
	}

	@Test
	public void testRadioButton() throws IOException, JSONException {
		required = 2;
		name = "RadioButton";
		ArrayList<String> nameslist = new ArrayList<String>(Arrays.asList("wo", "wa", "wee"));
		options = makeRadioButton(nameslist);
		testMethod();
		assertEquals(custom.getCustomdata().toString(), "[{\"name\":\""+name+"\",\"customdata_id\":"+custom.customDataCall.customID+",\"info\":{\"options\":"+ optionsArray +",\"input_type\":\""+custom.customDataCall.inputTypeMatch(inputType)+"\",\"required\":\""+custom.customDataCall.requiredMatch(required)+"\"}}]");
	}

	@Test
	public void testDropDownList() throws IOException, JSONException {
		required = 2;
		name = "DropDownList";
		ArrayList<String> nameslist = new ArrayList<String>(Arrays.asList("wo", "wa", "wee"));
		options = makeDropDownList(nameslist);
		testMethod();
		assertEquals(custom.getCustomdata().toString(), "[{\"name\":\""+name+"\",\"customdata_id\":"+custom.customDataCall.customID+",\"info\":{\"options\":"+ optionsArray +",\"input_type\":\""+custom.customDataCall.inputTypeMatch(inputType)+"\",\"required\":\""+custom.customDataCall.requiredMatch(required)+"\"}}]");
	}

	@Test
	public void testDateAndTime() throws IOException, JSONException {
		required = 2;
		name = "DateAndTime";
		options = makeDateAndTime(2); // timeType 1: only date, timeType 2: date and time
		testMethod();
		assertEquals(custom.getCustomdata().toString(), "[{\"name\":\""+name+"\",\"customdata_id\":"+custom.customDataCall.customID+",\"info\":{\"options\":"+ options +",\"input_type\":\""+custom.customDataCall.inputTypeMatch(inputType)+"\",\"required\":\""+custom.customDataCall.requiredMatch(required)+"\"}}]");
	}

	@Test
	public void testTextArea() throws IOException, JSONException {
		required = 2;
		name = "TextArea";
		options = makeTextArea(3, 2);
		testMethod();
		assertEquals(custom.getCustomdata().toString(), "[{\"name\":\""+name+"\",\"customdata_id\":"+custom.customDataCall.customID+",\"info\":{\"options\":"+ options +",\"input_type\":\""+custom.customDataCall.inputTypeMatch(inputType)+"\",\"required\":\""+custom.customDataCall.requiredMatch(required)+"\"}}]");
	}

	@Test
	public void testNumber() throws IOException, JSONException {
		required = 2;
		name = "TestNumber";
		options = makeNumber(1, 1, 100); //int defaultNum, int min, int max
		testMethod();
		options.put("interval", 1); // 1 for default
		assertEquals(custom.getCustomdata().toString(), "[{\"name\":\""+name+"\",\"customdata_id\":"+custom.customDataCall.customID+",\"info\":{\"options\":"+ options +",\"input_type\":\""+custom.customDataCall.inputTypeMatch(inputType)+"\",\"required\":\""+custom.customDataCall.requiredMatch(required)+"\"}}]");
	}

	@After
	public void after() throws IOException, JSONException {
		deleteCustomData();
	}

	private void testMethod() throws IOException, JSONException {
		customBody = makeCustomBody(name, inputType, required, options);
		putCustomData(customBody);
		System.out.println(customBody.toString());
		//custom = new CustomData("594c862a4bfe227e3a537be359d3381b","paulkim");	
		custom.getCustomdata();
	}


	private void deleteCustomData() throws IOException, JSONException {
		try {
			ArrayList<String> idList = new ArrayList<String>(custom.customDataCall.listCustomID());
			for (int i=0; i<idList.size(); i++) {
				HttpRequest request = new HttpRequest.Builder()
						.URL(URL+"paulkim"+FIELD+"/"+idList.get(i))
						.headers(headers)
						.build();
				String msg = request.delete();
				System.out.println("delete Custom Data Field "+(i+1)+"/"+idList.size()+": "+msg);
			}
		}
		catch (NullPointerException e) {
			System.out.println("Custom Data Field is empty");
			return;
		}
	}

	private String putCustomData(JSONObject customBody) throws IOException {
		RequestBody body = RequestBody.create(MediaType.parse("application/json"), customBody.toString());
		HttpRequest request = new HttpRequest.Builder()
				.URL(URL+"paulkim"+FIELD)
				.headers(headers)
				.setRawBody(body)
				.build();
		String msg = request.put();
		System.out.println(msg);
		return msg;
	}
	private JSONObject makeCustomBody(String name, int inputType, int required, JSONObject options) throws JSONException { //make custombody with textfield, dateandtime, textarea, makenumber
		JSONObject customBody = new JSONObject();
		customBody.put("options", options);
		customBody.put("required", required);
		customBody.put("input_type", inputType);
		customBody.put("name", name);
		return customBody;
	}

	private JSONObject makeTextField(int width, int max_length) throws JSONException {
		inputType = 1;
		JSONObject options = new JSONObject();
		options.put("max_length", max_length);
		options.put("width", width);
		return options;
	}

	private JSONObject makeRadioButton(ArrayList<String> names) throws JSONException {
		inputType = 3;
		JSONObject options = new JSONObject();
		JSONArray values = new JSONArray();
		for (int i=0; i<names.size(); i++) {
			values.put(names.get(i));
		}
		optionsArray = values;
		options.put("values", values);
		return options;
	}

	private JSONObject makeDropDownList(ArrayList<String> names) throws JSONException {
		inputType = 4;
		JSONObject options = new JSONObject();
		JSONArray values = new JSONArray();
		for (int i=0; i<names.size(); i++) {
			values.put(names.get(i));
		}
		optionsArray = values;
		options.put("values", values);
		return options;
	}

	private JSONObject makeDateAndTime(int timeType) throws JSONException { //timeType 1: only date, timeType 2: date and time
		inputType = 5;
		JSONObject options = new JSONObject();
		options.put("time", timeType);
		return options;
	}

	private JSONObject makeTextArea(int width, int rows) throws JSONException {
		inputType = 6;
		JSONObject options = new JSONObject();
		options.put("rows", rows);
		options.put("width", width);		
		return options;
	}

	private JSONObject makeNumber(int defaultNum, int min, int max) throws JSONException { 
		inputType = 7;
		JSONObject options = new JSONObject();
		options.put("min", min);
		options.put("max", max);
		options.put("default", defaultNum);
		System.out.println(options.toString());
		return options;
	}
}
