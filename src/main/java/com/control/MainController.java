package com.control;

import com.entity.*;

import com.service.Service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@RestController
@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = "com.entity")
@ComponentScan(basePackages = "com.service")
@RequestMapping("/busStation")
public class MainController {

    @Autowired
    private Service service;

    //curl -H "Content-type: application/json" -X POST -d '{"license":"YY0000UU", "name":"Valera", "surname":"Pupkin"}' http://localhost:8090/busStation/addDriver
    @RequestMapping(value = "/addDriver", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addDriver(@RequestBody Driver driver) {
        try {
            return ResponseEntity.ok(service.addDriver(driver));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //curl -H "Content-type: application/json" -X POST -d '{"number":"AA9898II", "model":"Ferrari"}' http://localhost:8090/busStation/addBus
    @RequestMapping(value = "/addBus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addBus(@RequestBody Bus bus) {
        try {
            return ResponseEntity.ok(service.addBus(bus));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //curl -H "Content-type: application/json" -X POST -d '{"number":"ERES1"}' http://localhost:8090/busStation/addVoyage
    @RequestMapping(value = "/addVoyage", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addVoyage(@RequestBody Voyage voyage) {
        try {
            return ResponseEntity.ok(service.addVoyage(voyage));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //curl -H "Content-type: application/json" -X POST http://localhost:8090/busStation/bus/{id}/driver/{driverId}
    @RequestMapping(value = "/bus/{id}/driver/{driverId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> changeDriverOnBus( @PathVariable(value="id") Integer busId,
                                                @PathVariable(value="driverId") Integer driverId) {
        try {
            return ResponseEntity.ok(service.changeDriverOnBus(busId, driverId));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //curl -H "Content-type: application/json" -X POST http://localhost:8090/busStation/voyage/{id}/bus/{busId}
    @RequestMapping(value = "/voyage/{id}/bus/{busId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> changeBusOnVoyage( @PathVariable(value="id") Integer voyageId,
                                                @PathVariable(value="busId") Integer busId) {
        try {
            return ResponseEntity.ok(service.changeBusOnVoyage(voyageId, busId));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //curl -H "Content-type: application/json" -X POST -d '{"place":1, "price":20}' http://localhost:8090/busStation/voyage/{id}/addTicket
    @RequestMapping(value = "/voyage/{id}/addTicket", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addTicketOnVoyage( @PathVariable(value="id") Integer voyageId,
                                                @RequestBody Ticket ticket) {
        Set<Ticket> tickets = new HashSet<>();
        tickets.add(ticket);
        try {
            return ResponseEntity.ok(service.addTicketsOnVoyage(voyageId, tickets));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //curl -H "Content-type: application/json" -X POST -d '[{"place":1, "price":20}, {"place":2, "price":20}]' http://localhost:8090/busStation/voyage/{id}/addTickets
    @RequestMapping(value = "/voyage/{id}/addTickets", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?>  addTicketsOnVoyage( @PathVariable(value="id") Integer voyageId,
                                      @RequestBody ArrayList<Ticket> tickets) {

        Set<Ticket> ticketSet = new HashSet<>(tickets);
        try {
            return ResponseEntity.ok(service.addTicketsOnVoyage(voyageId, ticketSet));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //curl -H "Content-type: application/json" -X POST http://localhost:8090/busStation/voyage/{id}/ticket/{ticketId}
    @RequestMapping(value = "/voyage/{id}/ticket/{ticketId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?>  sellTicket( @PathVariable(value="id") Integer voyageId,
                                          @PathVariable(value="ticketId") Integer ticketId) {
        try {
            return ResponseEntity.ok(service.sellTicket(voyageId, ticketId));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/findAllVoyages
    @RequestMapping(value = "/findAllVoyages", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findAllVoyages() {
        return ResponseEntity.ok(service.findAllVoyages());
    }

    //curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/findAllBuses
    @RequestMapping(value = "/findAllBuses", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findAllBuses() {
        return ResponseEntity.ok(service.findAllBuses());
    }

    //curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/findAllDrivers
    @RequestMapping(value = "/findAllDrivers", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findAllDrivers() {
        return ResponseEntity.ok(service.findAllDrivers());
    }

    //curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/findAllTickets
    @RequestMapping(value = "/findAllTickets", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findAllTickets() {
        return ResponseEntity.ok(service.findAllTickets());
    }

    //curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/voyage/{id}
    @RequestMapping(value = "/voyage/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findOneVoyage(@PathVariable(value="id") Integer id) {
        return ResponseEntity.ok(service.findOneVoyage(id));
    }

    //curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/bus/{id}
    @RequestMapping(value = "/bus/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findOneBus(@PathVariable(value="id") Integer id) {
        return ResponseEntity.ok(service.findOneBus(id));
    }

    //curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/driver/{id}
    @RequestMapping(value = "/driver/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findOneDriver(@PathVariable(value="id") Integer id) {
        return ResponseEntity.ok(service.findOneDriver(id));
    }

    //curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/ticket/{id}
    @RequestMapping(value = "/ticket/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findOneTicket(@PathVariable(value="id") Integer id) {
        return ResponseEntity.ok(service.findOneTicket(id));
    }
}
