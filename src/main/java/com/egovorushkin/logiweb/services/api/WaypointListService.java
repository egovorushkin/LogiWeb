package com.egovorushkin.logiweb.services.api;

import com.egovorushkin.logiweb.entities.WaypointList;

import java.util.List;

public interface WaypointListService {

    WaypointList getWaypointListById(int id);

    List<WaypointList> listAll();

    void saveWaypointList(WaypointList waypointList);

    void update(WaypointList waypointList);

    void delete(int id);

    WaypointList showWaypointList(int id);
}
