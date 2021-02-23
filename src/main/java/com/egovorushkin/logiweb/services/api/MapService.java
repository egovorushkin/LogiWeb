package com.egovorushkin.logiweb.services.api;

import com.google.maps.errors.ApiException;
import org.json.JSONObject;
import java.io.IOException;

/**
 * Service interface for Google Maps API
 * (Directions API
 * https://developers.google.com/maps/documentation/directions/overview)
 */
public interface MapService {

    Long parseJsonFromGoogleDirectionsApi(JSONObject jsonObject,
                                              String param1, String param2);

    int computeDistance(String fromCity, String toCity)
            throws InterruptedException, ApiException, IOException;

    int computeDuration(String fromCity, String toCity)
            throws InterruptedException, ApiException, IOException;

}
