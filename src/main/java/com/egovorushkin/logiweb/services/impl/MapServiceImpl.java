package com.egovorushkin.logiweb.services.impl;

import com.egovorushkin.logiweb.services.api.MapService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.io.IOException;

/**
 * Service class with business logics for Google Maps API
 * (Directions API
 * https://developers.google.com/maps/documentation/directions/overview)
 * implements {@link MapService}
 */
@Service
public class MapServiceImpl implements MapService {

    private static final String API_KEY =
            "AIzaSyArdSdjdLWeQtbx2V54Ro9YjThqU-y1TAM";
    private static final String DISTANCE = "distance";
    private static final String IN_METERS = "inMeters";
    private static final String DURATION = "duration";
    private static final String IN_SECONDS = "inSeconds";
    private static final String ROUTES = "routes";
    private static final String LEGS = "legs";


    @Override
    public int computeDistance(String fromCity, String toCity)
            throws InterruptedException, ApiException, IOException {

        return (int) (parseJsonFromGoogleDirectionsApi(
                responseJson(fromCity, toCity), DISTANCE, IN_METERS) / 1000);
    }

    @Override
    public int computeDuration(String fromCity, String toCity)
            throws InterruptedException, ApiException, IOException {

        return (int) (parseJsonFromGoogleDirectionsApi(
                responseJson(fromCity, toCity), DURATION, IN_SECONDS) / 3600);
    }

    @Override
    public Long parseJsonFromGoogleDirectionsApi(JSONObject jsonObject,
                                              String param1, String param2) {
        long result = 0;

        JSONArray arrRoutes = jsonObject.getJSONArray(ROUTES);

        for (int i = 0; i < arrRoutes.length(); i++) {
            JSONObject jObj = arrRoutes.getJSONObject(i);
            JSONArray arrLegs = jObj.getJSONArray(LEGS);

            for (int j = 0; j < arrLegs.length(); j++) {
                JSONObject jObj2 =
                        arrLegs.getJSONObject(j).getJSONObject(param1);
                result = jObj2.getLong(param2);
            }
        }
        return result;
    }

    private JSONObject responseJson(String fromCity, String toCity)
            throws ApiException, InterruptedException, IOException {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();

        DirectionsApiRequest directionsApiRequest =
                new DirectionsApiRequest(context).region("ru")
                        .origin(fromCity)
                        .destination(toCity);

        DirectionsResult resultOfDirections = directionsApiRequest.await();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return new JSONObject(gson.toJson(resultOfDirections));
    }
}
