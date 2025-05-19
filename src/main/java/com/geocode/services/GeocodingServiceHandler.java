package com.geocode.services;

import com.geocode.modal.Coordinates;
import com.geocode.exceptions.ApiException;
import com.geocode.exceptions.GeocodingException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class GeocodingServiceHandler implements GeocodingService {
    private static final String CONFIG_FILE = "config.properties";
    private static String API_BASE_URL;
    private static String API_KEY;
    private static final int LIMIT = 1;

    static {
        try (InputStream input = GeocodingServiceHandler.class
                .getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {

            Properties prop = new Properties();
            if (input == null) {
                throw new FileNotFoundException("Configuration file 'credential.properties' not found in classpath.");
            }
            prop.load(input);
            API_BASE_URL = prop.getProperty("api.base.url");
            API_KEY = prop.getProperty("api.key");

        } catch (IOException e) {
            throw new ExceptionInInitializerError("Failed to load API config: " + e.getMessage());
        }
    }

    @Override
    public Coordinates geocode(String placeName) throws GeocodingException, ApiException {
        try {
            String encodedPlaceName = URLEncoder.encode(placeName, StandardCharsets.UTF_8);
            String urlString = String.format("%s?q=%s&limit=%d&appid=%s",
                    API_BASE_URL, encodedPlaceName, LIMIT, API_KEY);

            String response = makeHttpRequest(urlString);
            return parseGeocodingResponse(response);

        } catch (IOException | ApiException e) {
            throw new ApiException("Error connecting to Geocoding API: " + e.getMessage(), e);
        }
    }

    private Coordinates parseGeocodingResponse(String response) throws GeocodingException, ApiException {
        try {
            JSONArray jsonArray = new JSONArray(response);

            if (jsonArray.length() == 0) {
                throw new ApiException("No results found for the given place");
            }

            JSONObject result = jsonArray.getJSONObject(0);
            double latitude = result.getDouble("lat");
            double longitude = result.getDouble("lon");
            String name = result.getString("name");
            String country = result.getString("country");

            return new Coordinates(latitude, longitude, name, country);

        } catch (Exception e) {
            throw new ApiException("Error parsing geocoding response: " + e.getMessage(), e);
        }
    }

    private String makeHttpRequest(String urlString) throws IOException, ApiException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new ApiException("Failed to connect to API. Response code: " + responseCode);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return response.toString();
    }
}
