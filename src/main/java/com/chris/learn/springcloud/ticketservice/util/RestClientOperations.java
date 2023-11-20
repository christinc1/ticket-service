package com.chris.learn.springcloud.ticketservice.util;


import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class RestClientOperations {


    public Double fetchTicketPrice(Long id) throws JSONException {
        String url = "http://localhost:8084/api/prices/" + id;
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(builder.toUriString(), String.class);
        Double price = null;

        if (response.getStatusCode() != null && response.getStatusCode().equals(HttpStatus.OK)) {
            if (response.getBody() != null) {
                JSONObject responseObj = new JSONObject(response.getBody());
                if (responseObj != null && responseObj.has("price")) {
                    Object priceObj = responseObj.get("price");
                    if (priceObj instanceof Number) {
                        price = ((Number) priceObj).doubleValue();
                    }
                }
            }
        }
        return price;

    }
}
