package com.egovorushkin.logiweb.controllers;

import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.GeocodedWaypoint;
import org.json.*;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/map")
public class MapController {

    private static final String API_KEY = "AIzaSyArdSdjdLWeQtbx2V54Ro9YjThqU" +
            "-y1TAM";

    @GetMapping
    public String showMap() throws InterruptedException, ApiException,
            IOException {

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();

        DirectionsApiRequest directionsApiRequest =
                new DirectionsApiRequest(context).origin("Moscow").destination("Omsk");

        DirectionsResult resultOfDirections = directionsApiRequest.await();

        GeocodedWaypoint[] geocodedWaypoints = resultOfDirections.geocodedWaypoints;
        for (GeocodedWaypoint gw : geocodedWaypoints) {
            System.out.println(gw.toString());
        }


        JSONObject jsonObject = new JSONObject(resultOfDirections);
        System.out.println(parseJsonFromGoogleDirections(jsonObject, "distance", "inMeters"));


        return "manager/map";
    }

    // TODO: replace to Service
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
