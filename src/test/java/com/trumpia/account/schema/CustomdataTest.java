package com.trumpia.account.schema;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trumpia.trumpia.schema.model.CustomData;
import com.trumpia.util.JSONUtils;
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
	ObjectNode options = JSONUtils.getNewObjectNode();
	ObjectNode customBody = JSONUtils.getNewObjectNode();
	ArrayNode optionsArray = JSONUtils.getNewArrayNode();

	@Before 
	public void before() {
		custom = new CustomData("594c862a4bfe227e3a537be359d3381b", "paulkim", "http://api.trumpia.com");
		headers.put("content-type", "application/json");
		headers.put("x-apikey", "594c862a4bfe227e3a537be359d3381b");
	}

	@Test
	public void testTextField() throws Exception {
		required = 2;
		name = "textfield";
		options = makeTextField(20, 200);
		System.out.println(options.toString());
		testMethod();
		assertEquals(custom.getCustomdata().toString(), "[{\"name\":\""+name+"\",\"customdata_id\":\""+custom.customDataCall.customID+"\",\"info\":{\"options\":"+ options +",\"input_type\":\""+custom.customDataCall.inputTypeMatch(inputType)+"\",\"required\":\""+custom.customDataCall.requiredMatch(required)+"\"}}]");
	}

	@Test
	public void testRadioButton() throws Exception {
		required = 2;
		name = "RadioButton";
		ArrayList<String> nameslist = new ArrayList<String>(Arrays.asList("wo", "wa", "wee"));
		options = makeRadioButton(nameslist);
		System.out.println(options.toString());
		testMethod();
		assertEquals(custom.getCustomdata().toString(), "[{\"name\":\""+name+"\",\"customdata_id\":\""+custom.customDataCall.customID+"\",\"info\":{\"options\":"+ optionsArray +",\"input_type\":\""+custom.customDataCall.inputTypeMatch(inputType)+"\",\"required\":\""+custom.customDataCall.requiredMatch(required)+"\"}}]");
	}

	@Test
	public void testDropDownList() throws Exception {
		required = 2;
		name = "DropDownList";
		ArrayList<String> nameslist = new ArrayList<String>(Arrays.asList("wo", "wa", "wee"));
		options = makeDropDownList(nameslist);
		testMethod();
		assertEquals(custom.getCustomdata().toString(), "[{\"name\":\""+name+"\",\"customdata_id\":\""+custom.customDataCall.customID+"\",\"info\":{\"options\":"+ optionsArray +",\"input_type\":\""+custom.customDataCall.inputTypeMatch(inputType)+"\",\"required\":\""+custom.customDataCall.requiredMatch(required)+"\"}}]");
	}

	@Test
	public void testDateAndTime() throws Exception {
		required = 2;
		name = "DateAndTime";
		options = makeDateAndTime(2); // timeType 1: only date, timeType 2: date and time
		testMethod();
		assertEquals(custom.getCustomdata().toString(), "[{\"name\":\""+name+"\",\"customdata_id\":\""+custom.customDataCall.customID+"\",\"info\":{\"options\":"+ options +",\"input_type\":\""+custom.customDataCall.inputTypeMatch(inputType)+"\",\"required\":\""+custom.customDataCall.requiredMatch(required)+"\"}}]");
	}

	@Test
	public void testTextArea() throws Exception {
		required = 2;
		name = "TextArea";
		options = makeTextArea(3, 2);
		testMethod();
		assertEquals(custom.getCustomdata().toString(), "[{\"name\":\""+name+"\",\"customdata_id\":\""+custom.customDataCall.customID+"\",\"info\":{\"options\":"+ options +",\"input_type\":\""+custom.customDataCall.inputTypeMatch(inputType)+"\",\"required\":\""+custom.customDataCall.requiredMatch(required)+"\"}}]");
	}

	@Test
	public void testNumber() throws Exception {
		required = 2;
		name = "TestNumber";
		options = makeNumber(1, 1, 100); //int defaultNum, int min, int max
		testMethod();
		options.put("interval", 1); // 1 for default
		assertEquals(custom.getCustomdata().toString(), "[{\"name\":\""+name+"\",\"customdata_id\":\""+custom.customDataCall.customID+"\",\"info\":{\"options\":"+ options +",\"input_type\":\""+custom.customDataCall.inputTypeMatch(inputType)+"\",\"required\":\""+custom.customDataCall.requiredMatch(required)+"\"}}]");
	}

	@After
	public void after() throws Exception {
		deleteCustomData();
	}

	private void testMethod() throws Exception {
		customBody = makeCustomBody(name, inputType, required, options);
		putCustomData(customBody);
		System.out.println(customBody.toString());
		//custom = new CustomData("594c862a4bfe227e3a537be359d3381b","paulkim");	
		custom.getCustomdata();
	}


	private void deleteCustomData() throws Exception {
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

	private String putCustomData(ObjectNode customBody) throws IOException {
		RequestBody body = RequestBody.create(MediaType.parse("application/json"), customBody.toString());
		System.out.println(customBody.toString());
		HttpRequest request = new HttpRequest.Builder()
				.URL(URL+"paulkim"+FIELD)
				.headers(headers)
				.setRawBody(body)
				.build();
		String msg = request.put();
		System.out.println("msg: "+msg);
		return msg;
	}
	private ObjectNode makeCustomBody(String name, int inputType, int required, ObjectNode options) { //make custombody with textfield, dateandtime, textarea, makenumber
		ObjectNode customBody = JSONUtils.getNewObjectNode();
		customBody.set("options", options);
		customBody.put("required", required);
		customBody.put("input_type", inputType);
		customBody.put("name", name);
		System.out.println("customBody: "+customBody);
		return customBody;
	}

	private ObjectNode makeTextField(int width, int max_length) {
		inputType = 1;
		options = JSONUtils.getNewObjectNode();
		options.put("width", width);
		options.put("max_length", max_length);
		return options;
	}

	private ObjectNode makeRadioButton(ArrayList<String> names) {
		inputType = 3;
		options = JSONUtils.getNewObjectNode();
		ArrayNode values = JSONUtils.getNewArrayNode();
		for (int i=0; i<names.size(); i++) {
			values.add(names.get(i));
		}
		optionsArray = values;
		options.set("values", values);
		return options;
	}

	private ObjectNode makeDropDownList(ArrayList<String> names) {
		inputType = 4;
		options = JSONUtils.getNewObjectNode();
		ArrayNode values = JSONUtils.getNewArrayNode();
		for (int i=0; i<names.size(); i++) {
			values.add(names.get(i));
		}
		optionsArray = values;
		options.set("values", values);
		return options;
	}

	private ObjectNode makeDateAndTime(int timeType) { //timeType 1: only date, timeType 2: date and time
		inputType = 5;
		options = JSONUtils.getNewObjectNode();
		options.put("time", timeType);
		return options;
	}

	private ObjectNode makeTextArea(int width, int rows) {
		inputType = 6;
		options = JSONUtils.getNewObjectNode();
		options.put("width", width);	
		options.put("rows", rows);
		return options;
	}

	private ObjectNode makeNumber(int defaultNum, int min, int max) { 
		inputType = 7;
		options = JSONUtils.getNewObjectNode();
		options.put("default", defaultNum);
		options.put("min", min);
		options.put("max", max);
//		System.out.println(options.toString());
		return options;
	}
}
