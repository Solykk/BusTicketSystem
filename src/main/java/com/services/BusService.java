package com.services;

import com.entity.Bus;

import java.util.List;

public interface BusService {

    Bus addBus(Bus bus);
    Bus changeDriverOnBus(Integer busId, Integer driverId);
    Bus findOneBus(Integer id);
    List<Bus> findAllBuses();

}
