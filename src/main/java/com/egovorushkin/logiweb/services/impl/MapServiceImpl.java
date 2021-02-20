package com.egovorushkin.logiweb.services.impl;

import com.egovorushkin.logiweb.dto.OrderDto;
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

@Service
public class MapServiceImpl implements MapService {

    private static final String API_KEY = "AIzaSyArdSdjdLWeQtbx2V54Ro9YjThqU" +
            "-y1TAM";

    @Override
    public OrderDto evaluateDistanceAndDuration(OrderDto orderDto)
            throws InterruptedException, ApiException, IOException {

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();

        DirectionsApiRequest directionsApiRequest =
                new DirectionsApiRequest(context)
                        .origin(orderDto.getFromCity().getName())
                        .destination(orderDto.getToCity().getName());

        DirectionsResult resultOfDirections = directionsApiRequest.await();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        System.out.println(gson.toJson(resultOfDirections));

        JSONObject jsonObj = new JSONObject(gson.toJson(resultOfDirections));


        orderDto.setDistance((int) (parseJsonFromGoogleDirections(
                        jsonObj, "distance", "inMeters") / 1000));
        orderDto.setDuration((int) (parseJsonFromGoogleDirections(
                jsonObj, "duration", "inSeconds") / 3600));

        return null;
    }
    @Override
    public long parseJsonFromGoogleDirections(JSONObject jsonObject,
                                              String param1, String param2) {
        long result = 0;

        JSONArray arrRoutes = jsonObject.getJSONArray("routes");

        for (int i = 0; i < arrRoutes.length(); i++) {
            JSONObject jObj = arrRoutes.getJSONObject(i);
            JSONArray arrLegs = jObj.getJSONArray("legs");

            for (int j = 0; j < arrLegs.length(); j++) {
                JSONObject jObj2 =
                        arrLegs.getJSONObject(j).getJSONObject(param1);
                result = jObj2.getLong(param2);
            }
        }
        return result;
    }
}
