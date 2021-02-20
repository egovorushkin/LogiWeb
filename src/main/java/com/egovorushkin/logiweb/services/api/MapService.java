package com.egovorushkin.logiweb.services.api;

import com.egovorushkin.logiweb.dto.OrderDto;
import com.google.maps.errors.ApiException;
import org.json.JSONObject;

import java.io.IOException;

public interface MapService {

    long parseJsonFromGoogleDirections(JSONObject jsonObject,
                                              String param1, String param2);

    OrderDto evaluateDistanceAndDuration(OrderDto orderDto)
            throws InterruptedException, ApiException, IOException;
}
