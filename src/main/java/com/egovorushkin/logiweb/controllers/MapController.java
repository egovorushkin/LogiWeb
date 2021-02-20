package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.services.api.MapService;
import com.egovorushkin.logiweb.utils.directions.DirectionRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.*;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/map")
public class MapController {

    private final MapService mapService;

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }


//    @GetMapping
//    public String showMap() throws InterruptedException, ApiException,
//            IOException {
//
//        GeoApiContext context = new GeoApiContext.Builder()
//                .apiKey(API_KEY)
//                .build();
//
//        DirectionsApiRequest directionsApiRequest =
//                new DirectionsApiRequest(context).origin("Moscow").destination("Omsk");
//
//        DirectionsResult resultOfDirections = directionsApiRequest.await();
//
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        //System.out.println(gson.toJson(resultOfDirections));
//
//        JSONObject jsonObject = new JSONObject(gson.toJson(resultOfDirections));
//
//
//        System.out.println(parseJsonFromGoogleDirections(jsonObject, "distance", "inMeters"));
//        System.out.println(parseJsonFromGoogleDirections(jsonObject, "duration", "inSeconds"));
//
//
//        return "manager/map";
//    }

    @GetMapping()
    public String dirReq(Model model) {
        model.addAttribute("dirReq", new DirectionRequest());
        return "/manager/map";
    }

    @PostMapping("/req")
    public void dirReq(@ModelAttribute("dirReq") DirectionRequest dirReq) throws InterruptedException, ApiException, IOException {

//        DirectionsApiRequest directionsApiRequest =
//                new DirectionsApiRequest(context).origin(dirReq.getOrigin()).destination(dirReq.getDestination());
//
//        DirectionsResult resultOfDirections = directionsApiRequest.await();
//
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //System.out.println(gson.toJson(resultOfDirections));

//        JSONObject jsonObject = new JSONObject(gson.toJson(resultOfDirections));


//        System.out.println(parseJsonFromGoogleDirections(jsonObject, "distance", "inMeters") / 1000);
//        System.out.println(parseJsonFromGoogleDirections(jsonObject, "duration", "inSeconds") / 3600);
    }

//    // TODO: replace to Service
//    public long parseJsonFromGoogleDirections(JSONObject jsonObject,
//                                              String param1, String param2) {
//        long result = 0;
//
//        JSONArray arrRoutes = jsonObject.getJSONArray("routes");
//
//        for (int i = 0; i < arrRoutes.length(); i++) {
//            JSONObject jObj = arrRoutes.getJSONObject(i);
//            JSONArray arrLegs = jObj.getJSONArray("legs");
//
//            for (int j = 0; j < arrLegs.length(); j++) {
//                JSONObject jObj2 =
//                        arrLegs.getJSONObject(j).getJSONObject(param1);
//                result = jObj2.getLong(param2);
//            }
//        }
//        return result;
//    }

}
