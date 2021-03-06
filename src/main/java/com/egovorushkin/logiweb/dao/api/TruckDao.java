package com.egovorushkin.logiweb.dao.api;

import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.Truck;

import java.util.List;

/**
 * This interface represent some methods to operate on a {@link Truck}
 * (retrieve from and save to database)
 */
public interface TruckDao {

    Truck getTruckById(Long id);

    List<Truck> getAllTrucks();

    void createTruck(Truck truck);

    void updateTruck(Truck truck);

    void deleteTruck(Long id);

    List<Driver> findAvailableDriversByTruck(Truck truck);

    boolean truckExistsByRegistrationNumber(String registrationNumber);

    Truck findByRegistrationNumber(String registrationNumber);

    List<Truck> listAllByPage(int firstResult, int maxResult);

    Long totalCount();

}
