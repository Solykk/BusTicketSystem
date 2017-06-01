package com.controllers;

import com.entity.Bus;

import com.services.BusService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/busStation")
public class BusController {

    private BusService service;

    //curl -H "Content-type: application/json" -X POST -d '{"number":"AA9898II", "model":"Ferrari"}' http://localhost:8090/busStation/buses
    @RequestMapping(value = "/buses", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addBus(@RequestBody Bus bus) {
        return ResponseEntity.ok(service.addBus(bus));
    }

    //curl -H "Content-type: application/json" -X PUT http://localhost:8090/busStation/buses/{id}/drivers/{driverId}
    @RequestMapping(value = "/buses/{id}/drivers/{driverId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> changeDriverOnBus( @PathVariable(value="id") Integer busId,
                                                @PathVariable(value="driverId") Integer driverId) {
        return ResponseEntity.ok(service.changeDriverOnBus(busId, driverId));
    }

    //curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/buses/{id}
    @RequestMapping(value = "/buses/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findOneBus(@PathVariable(value="id") Integer id) {
        return ResponseEntity.ok(service.findOne(id));
    }

    //curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/buses
    @RequestMapping(value = "/buses", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findAllBuses() {
        return ResponseEntity.ok(service.findAll());
    }

    @Autowired
    public void setService(BusService service) {
        this.service = service;
    }
}
