package com.trumpia.util.Http;

import static com.trumpia.util.LogUtils.getLogger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trumpia.dynamics.views.DynamicsController;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class HttpRequest {
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	public static final OkHttpClient client = new OkHttpClient();
	
		private final RequestBody body;
		private final Request.Builder request;
		
		private HttpRequest (RequestBody body, Request.Builder request) {
			this.body = body;
			this.request = request;
		}
		
		public String get() throws IOException {
			String response = null;
			Response responseObj = null;
			try {
				responseObj = client.newCall(request.get().build())
						   .execute();
				if (responseObj.code() >= 400) {
					throw new UnsuccessfulRequestException("Content : " + response + "\nStatus code : " + String.valueOf(responseObj.code()), responseObj.code());
				}
				response = responseObj.body().string();
			} catch (Exception e) {
				throw e;
			} finally {
				if (responseObj != null)
					responseObj.body().close();
			}
			return response;
		}
		
		public String post() throws IOException {
			String response = null;
			Response responseObj = null;
			try {
				responseObj = client.newCall(request.post(body).build())
						   .execute();
				if (responseObj.code() >= 400) {
					getLogger(HttpRequest.class).error(responseObj.message());
					getLogger(HttpRequest.class).error(responseObj.body().string());
					final Buffer buffer = new Buffer();
			        body.writeTo(buffer);
					getLogger(HttpRequest.class).error(buffer.readUtf8());
					throw new UnsuccessfulRequestException("Content : " + response + "\nStatus code : " + String.valueOf(responseObj.code()), responseObj.code());
				}
				response = responseObj.body().string();
			} catch (Exception e) {
				throw e;
			} finally {
				if (responseObj != null)
					responseObj.body().close();
			}
			return response;
		}
		
		public String put() throws IOException {
			String response = null;
			Response responseObj = null;
			try {
				responseObj = client.newCall(request.put(body).build())
						   .execute();
				response = responseObj.body().string();
				if (responseObj.code() >= 400) {
					getLogger(HttpRequest.class).error(responseObj.message());
					getLogger(HttpRequest.class).error(responseObj.body().string());
					final Buffer buffer = new Buffer();
			        body.writeTo(buffer);
					getLogger(HttpRequest.class).error(buffer.readUtf8());
					throw new UnsuccessfulRequestException("Content : " + response + "\nStatus code : " + String.valueOf(responseObj.code()), responseObj.code());
				}
			} catch (Exception e) {
				throw e;
			} finally {
				if (responseObj != null)
					responseObj.body().close();
			}
			return response;
		}
		
		public String delete() throws IOException {
			String response = null;
			Response responseObj = null;
			try {
				responseObj = client.newCall(request.delete().build())
						   .execute();
				if (responseObj.code() >= 400) {
					throw new UnsuccessfulRequestException("Content : " + response + "\nStatus code : " + String.valueOf(responseObj.code()), responseObj.code());
				}
				response = responseObj.body().string();
			} catch (Exception e) {
				throw e;
			} finally {
				if (responseObj != null)
					responseObj.body().close();
			}
			return response;
		}
		
		
		public static class Builder {
			private String URL;
			private Map<String, String> headers;
			private Map<String, String> queryParameters;
			private JsonNode JsonPayload;
			private RequestBody rawBody;
			
			public Builder URL(String URL) {
				this.URL = URL;
				return this;
			}
			
			public Builder headers(Map<String, String> headers) {
				this.headers = headers;
				return this;
			}
			
			public Builder setRawBody(RequestBody raw) {
				this.rawBody = raw;
				return this;
			}
			
			public Builder queryParameters(HttpUrl tempUrl) {
				HashMap<String, String> queryparams = new HashMap<String,String>();
				for (String queryParameterName : tempUrl.queryParameterNames()) {
					queryparams.put(queryParameterName, tempUrl.queryParameter(queryParameterName));
				}
				this.queryParameters = queryparams;
				return this;
			}
			
			public Builder JsonPayload (JsonNode JsonPayload) {
				this.JsonPayload = JsonPayload;
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json");
				this.headers(headers);
				return this;
			}
			
			private RequestBody getRequestBody() throws JsonProcessingException {
				if (this.rawBody != null)
					return rawBody;
				ObjectMapper JsonMapper = new ObjectMapper();
				if (this.JsonPayload != null)
					return RequestBody.create(JSON, JsonMapper.writeValueAsString(JsonPayload));
				return null;
			}
			
			private Request.Builder setHeaders(Request.Builder request) {
				Request.Builder temp = request;
				if (this.headers != null) {
					for (Map.Entry<String, String> header : this.headers.entrySet())
						temp = request.header(header.getKey(), header.getValue());
				}
				return temp;
			}
			
			private HttpUrl.Builder setUrl(HttpUrl.Builder builder, HttpUrl tempUrl) {
				HttpUrl.Builder temp = builder;
				if (tempUrl.pathSize() > 0)
					temp = temp.addPathSegments(tempUrl.pathSegments().stream().collect(Collectors.joining("/")));
				return temp;
			}
			
			private HttpUrl.Builder setUrlBase(HttpUrl tempHttpUrl) {
				if (tempHttpUrl == null)
					throw new IllegalArgumentException("Invalid URL");
				return new HttpUrl.Builder().scheme(tempHttpUrl.scheme()).host(tempHttpUrl.host()).port(tempHttpUrl.port()) ;
			}
			
			private HttpUrl.Builder setQueryParameters(HttpUrl.Builder builder) {
				HttpUrl.Builder temp = builder;
				if (this.queryParameters != null)
					for (Map.Entry<String, String> queryParameter : this.queryParameters.entrySet())
						temp = temp.addQueryParameter(queryParameter.getKey(), queryParameter.getValue());
				return temp;	
			}
			
			public HttpRequest build() throws JsonProcessingException {
				RequestBody requestBody = getRequestBody();
				Request.Builder request = new Request.Builder();
				HttpUrl tempHttpUrl = HttpUrl.parse(this.URL);
				HttpUrl.Builder url = setUrlBase(tempHttpUrl);
				this.queryParameters(tempHttpUrl);
				request = setHeaders(request);
				url = setUrl(url, tempHttpUrl);
				url = setQueryParameters(url);
				request = request.url(url.build());
				getLogger(HttpRequest.class).debug("URL : {}, body: {}", url, requestBody.toString());
				return new HttpRequest(requestBody, request);
				
				
			}
			
		}
		
		public class UnsuccessfulRequestException extends RuntimeException {
			private static final long serialVersionUID = 6232507272902455862L;
			int statusCode;
			
			public int getStatusCode() {
				return this.statusCode;
			}
			public UnsuccessfulRequestException(String message, int statusCode) {
				super(message);
				this.statusCode = statusCode;
			}
		}
}
