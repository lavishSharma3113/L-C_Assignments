package com.learnandcode;

import org.json.JSONObject;

public class JsonParserService {

    public String cleanApiResponse(String responseData) throws Exception {
        if (responseData.startsWith("var tumblr_api_read =")) {
            responseData = responseData.replace("var tumblr_api_read =", "").trim();
            responseData = responseData.endsWith(";") ? responseData.substring(0, responseData.length() - 1) : responseData;
            return responseData;
        } else {
            throw new Exception("Unexpected response format from Tumblr API.");
        }
    }
    

    public JSONObject parseJson(String cleanJson) {
        return new JSONObject(cleanJson);
    }
}


