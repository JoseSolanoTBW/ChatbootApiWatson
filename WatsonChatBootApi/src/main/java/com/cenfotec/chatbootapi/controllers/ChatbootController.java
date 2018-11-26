package com.cenfotec.chatbootapi.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cenfotec.chatbootapi.objects.ChatMessage;
import com.cenfotec.chatbootapi.objects.ChatResponse;

@RestController
@RequestMapping("/chatboot")
public class ChatbootController {

	@CrossOrigin
	@GetMapping("/talk")
    public ChatResponse Conversation (@RequestBody ChatMessage objSent)
	{		 
		ChatResponse response = new ChatResponse();
		
		return  response;
    }
	
}
