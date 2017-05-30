package com.services;

import com.entity.Driver;

import java.util.List;

public interface DriverService {

    Driver addDriver(Driver driver);
    List<Driver> findAllDrivers();
    Driver findOneDriver(Integer id);

}
