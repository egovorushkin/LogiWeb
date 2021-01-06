package com.egovorushkin.logiweb.services.impl;

import com.egovorushkin.logiweb.dao.api.WaypointListDao;
import com.egovorushkin.logiweb.entities.WaypointList;
import com.egovorushkin.logiweb.services.api.WaypointListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WaypointListServiceImpl implements WaypointListService {

    private final WaypointListDao waypointListDao;

    @Autowired
    public WaypointListServiceImpl(WaypointListDao waypointListDao) {
        this.waypointListDao = waypointListDao;
    }

    @Override
    public WaypointList getWaypointListById(int id) {
        return waypointListDao.getWaypointListById(id);
    }

    @Override
    public List listAll() {
        return waypointListDao.listAll();
    }

    @Override
    public WaypointList showWaypointList(int id) {
        return waypointListDao.showWaypointList(id);
    }

    @Override
    @Transactional
    public void saveWaypointList(WaypointList waypointList) {
        waypointListDao.saveWaypointList(waypointList);
    }

    @Override
    @Transactional
    public void update(WaypointList waypointList) {
        waypointListDao.update(waypointList);
    }

    @Override
    @Transactional
    public void delete(int id) {
        waypointListDao.delete(id);
    }

}
