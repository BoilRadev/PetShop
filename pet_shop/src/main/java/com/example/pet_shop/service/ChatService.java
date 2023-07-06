package com.example.pet_shop.service;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.example.pet_shop.controller.ChatController.CHATGPT_API_URL;


@Service
public class ChatService {

    private HttpClient httpClient;

    public ChatService() {
        this.httpClient = HttpClientBuilder.create().build();
    }

    public String sendMessageToChatGPT(String message) {
        try {

            HttpPost request = new HttpPost(CHATGPT_API_URL);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Authorization", "Bearer ");  // insert your Bearer token


            JSONObject requestBody = new JSONObject();
            requestBody.put("message", message);
            requestBody.put("max_tokens", 100);

            request.setEntity(new StringEntity(requestBody.toString()));


            HttpResponse response = httpClient.execute(request);


            int statusCode = response.getCode();
            if (statusCode == HttpStatus.SC_OK) {
                String responseBody = EntityUtils.toString((HttpEntity) response);

                JSONObject json = new JSONObject(responseBody);
                String generatedMessage = json.getJSONArray("choices").getJSONObject(0).getString("text");
                return generatedMessage;
            } else {
                return "Error occurred while processing the request";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occurred while communicating with ChatGPT";
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}