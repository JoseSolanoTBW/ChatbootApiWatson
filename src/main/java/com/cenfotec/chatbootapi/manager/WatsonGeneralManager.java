package com.cenfotec.chatbootapi.manager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.cenfotec.chatbootapi.objects.ChatMessage;
import com.cenfotec.chatbootapi.objects.ChatResponse;
import com.cenfotec.chatbootapi.objects.WatsonMessageResponse;
import com.cenfotec.chatbootapi.objects.WatsonSessionResponse;
import com.google.gson.Gson;

public class WatsonGeneralManager {
	
	public static ChatResponse talk(ChatMessage message) throws ClientProtocolException, IOException {
		
		ChatResponse response =  new ChatResponse();

		if(message.SessionId == null || message.SessionId == "") {
			message.SessionId = getSessionId();			
		}		
		
		response.SessionId =  message.SessionId;
		response.Response = sendMessage(message.SessionId, message.Message);
				
		return response;
	}	
	
	private static HttpPost generateHttpPost(String url) {
		return new HttpPost(url);
	}
	
	private static String getSessionId() throws ClientProtocolException, IOException {
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost post = generateHttpPost("https://gateway.watsonplatform.net/assistant/api/v2/assistants/832553b6-4165-4528-8d76-0351b0654b73/sessions?version=2018-11-27");
		post  = addBasicAuthentication(post);
		
		//Execute and get the response.
		HttpResponse response = httpclient.execute(post);
				
		//Read Response
		String json_string = EntityUtils.toString(response.getEntity());
		WatsonSessionResponse data = new Gson().fromJson(json_string, WatsonSessionResponse.class);
		
		return data.session_id;
	}	
	
	private static HttpPost  addBasicAuthentication(HttpPost connection) throws UnsupportedEncodingException {
	
		String encoding = Base64.getEncoder().encodeToString(("apikey:63y8Sjay2SwXHfCH84a6o0CCW16jcT1rrw4CNhzC4_aW").getBytes("UTF-8"));	
		connection.setHeader("Authorization", "Basic " + encoding);
		connection.setHeader("Accept", "application/json");
		connection.setHeader("Content-type", "application/json");
		return connection;
		
	}
	
	private static String sendMessage(String sessionId, String userMessage) throws ClientProtocolException, IOException {
		
		HttpClient httpclient = HttpClients.createDefault();
		String url = "https://gateway.watsonplatform.net/assistant/api/v2/assistants/832553b6-4165-4528-8d76-0351b0654b73/sessions/"+ sessionId +"/message?version=2018-11-27"; 
		
		HttpPost post = generateHttpPost(url);
		post  = addBasicAuthentication(post);
		
		String json = "{ \"input\" : {  \"text\" : \"" +  userMessage + "\"} }";
		
		StringEntity entity = new StringEntity(json);
		post.setEntity(entity);
		
		//Execute and get the response.
		HttpResponse response = httpclient.execute(post);
				
		//Read Response
		String json_string = EntityUtils.toString(response.getEntity());
		WatsonMessageResponse data = new Gson().fromJson(json_string, WatsonMessageResponse.class);
		
		return data.output.generic.get(0).text;		
	}
}
