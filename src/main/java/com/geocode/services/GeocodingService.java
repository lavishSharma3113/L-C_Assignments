package com.geocode.services;

import com.geocode.exceptions.ApiException;
import com.geocode.modal.Coordinates;
import com.geocode.exceptions.GeocodingException;

public interface GeocodingService {
    Coordinates geocode(String placeName) throws GeocodingException, ApiException;
}
