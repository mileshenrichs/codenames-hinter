package com.henrichs.codenames;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AssociationClient {

    @Value("${twinword.apikey}")
    private String apiKey;

    public List<Association> getAssociations(String word) throws Exception {
        System.out.println(apiKey + " is my key");
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet("https://twinword-word-associations-v1.p.rapidapi.com/associations/?entry=" + word);

            // add request headers
            request.addHeader("x-rapidapi-host", "twinword-word-associations-v1.p.rapidapi.com");
            request.addHeader("x-rapidapi-key", apiKey);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                // Get HttpResponse Status
                System.out.println(response.getProtocolVersion());              // HTTP/1.1
                System.out.println(response.getStatusLine().getStatusCode());   // 200
                System.out.println(response.getStatusLine().getReasonPhrase()); // OK
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entity);
                    return jsonToAssociationList(word, result);
                }
            }
        }
        throw new Exception();
    }

    private List<Association> jsonToAssociationList(String originalWord, String jsonResponse) {
        List<Association> associations = new ArrayList<>();
        JSONObject resObj = new JSONObject(jsonResponse);
        JSONObject associationScores = resObj.getJSONObject("associations_scored");
        for (String word : associationScores.keySet()) {
            double strength = (double) associationScores.get(word);
            associations.add(new Association(originalWord, word, strength));
        }
        return associations;
    }
}
