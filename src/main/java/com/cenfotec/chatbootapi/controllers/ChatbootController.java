package com.cenfotec.chatbootapi.controllers;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cenfotec.chatbootapi.manager.WatsonGeneralManager;
import com.cenfotec.chatbootapi.objects.ChatMessage;
import com.cenfotec.chatbootapi.objects.ChatResponse;

@RestController
@RequestMapping("/chatboot")
public class ChatbootController {

	@CrossOrigin
	@RequestMapping(value="/talk", method=RequestMethod.POST)
    public ChatResponse Conversation (@RequestBody ChatMessage objSent) throws ClientProtocolException, IOException
	{		 
		return  WatsonGeneralManager.talk(objSent);			
    }
	
}
