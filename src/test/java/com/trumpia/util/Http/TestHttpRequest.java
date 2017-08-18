package com.trumpia.util.Http;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TestHttpRequest {

	@Test
	public void test() throws IOException {
		HashMap<String, String> headers = new HashMap<String, String>();

		headers.put("content-type", "application/json");
		headers.put("x-apikey", "594c862a4bfe227e3a537be359d3381b");
		HttpRequest request = new HttpRequest.Builder()
				.URL("http://api.trumpia.com/rest/v1/paulkim/subscription")
				.headers(headers)
				.build();
		String customDataListString = request.get();
		System.out.println(customDataListString);
	}
	
	@Test
	public void test2() throws IOException {
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\r\n  \"name\" : \"Age\",\r\n  \"input_type\" : 7,\r\n  \"required\" : 1\r\n}");
		Request request = new Request.Builder()
		  .url("http://api.trumpia.com/rest/v1/paulkim/customdata")
		  .get()
		  .addHeader("content-type", "application/json")
		  .addHeader("x-apikey", "594c862a4bfe227e3a537be359d3381b")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("postman-token", "ed880da8-65d8-3bd3-5414-5ab4d500ad94")
		  .build();

		Response response = client.newCall(request).execute();
	}

}
