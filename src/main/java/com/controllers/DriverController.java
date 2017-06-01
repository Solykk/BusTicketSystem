package com.controllers;

import com.entity.Driver;

import com.services.DriverService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/busStation")
public class DriverController {

    private DriverService service;


    //curl -H "Content-type: application/json" -X POST -d '{"license":"YY0000UU", "name":"Valera", "surname":"Pupkin"}' http://localhost:8090/busStation/drivers
    @RequestMapping(value = "/drivers", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addDriver(@RequestBody Driver driver) {
        return ResponseEntity.ok(service.addDriver(driver));
    }

    //curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/drivers/{id}
    @RequestMapping(value = "/drivers/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findOneDriver(@PathVariable(value="id") Integer id) {
        return ResponseEntity.ok(service.findOne(id));
    }

    //curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/drivers
    @RequestMapping(value = "/drivers", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findAllDrivers() {
        return ResponseEntity.ok(service.findAll());
    }

    @Autowired
    public void setService(DriverService service) {
        this.service = service;
    }
}
