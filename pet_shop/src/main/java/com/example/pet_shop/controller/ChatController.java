package com.example.pet_shop.controller;

import com.example.pet_shop.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatController {

    public static final String CHATGPT_API_URL = "https://api.openai.com/v1/engines/davinci-codex/completions";

    @Autowired
    private ChatService chatService;

    @RequestMapping("/chat")
    public String chatPage() {
        return "chat"; // Return the name of your chat page view
    }

    @RequestMapping(value = "/chat", method = RequestMethod.POST)
    @ResponseBody
    public String sendMessage(@RequestBody String message) {
        String response = chatService.sendMessageToChatGPT(message);
        // Perform any additional processing or logic with the response
        return response;
    }
}
