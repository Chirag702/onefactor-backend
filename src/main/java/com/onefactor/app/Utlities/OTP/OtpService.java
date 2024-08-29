package com.onefactor.app.Utlities.OTP;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class OtpService {

    @Value("${messagecentral.auth.token}")
    private String authToken;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper; // Jackson ObjectMapper for parsing JSON

    public OtpService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public String sendOtp(String mobileNumber) {
        
        String url = "https://cpaas.messagecentral.com/verification/v3/send";
        
        try {
            // Build the request payload
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("countryCode", "91");
            requestBody.add("flowType", "SMS");
            requestBody.add("mobileNumber", mobileNumber);
            requestBody.add("otpLength", "4"); // Adjust based on API docs

            // Set the headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.set("authToken", authToken);

            // Log the request details for debugging
            System.out.println("Request URL: " + url);
            System.out.println("Request Headers: " + headers);
            System.out.println("Request Body: " + requestBody);

            // Build the HttpEntity with the request body and headers
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(requestBody, headers);

            // Send the POST request
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

            System.out.println("Response: " + response.getBody()); // Log the response

            // Parse the response to extract verificationId
            JsonNode jsonResponse = objectMapper.readTree(response.getBody());
            JsonNode dataNode = jsonResponse.path("data");
            String verificationId = dataNode.path("verificationId").asText();

            return verificationId;
        } catch (Exception e) {
            e.printStackTrace(); // Log the full exception stack trace
            return e.getMessage();
        }
    }
}
