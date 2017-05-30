package com.controllers;

import com.entity.Ticket;
import com.entity.Voyage;

import com.services.VoyageService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/busStation")
public class VoyageController {

    private VoyageService service;

    //curl -H "Content-type: application/json" -X POST -d '{"number":"ERES1"}' http://localhost:8090/busStation/addVoyage
    @RequestMapping(value = "/addVoyage", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addVoyage(@RequestBody Voyage voyage) {
        return ResponseEntity.ok(service.addVoyage(voyage));
    }

    //curl -H "Content-type: application/json" -X POST http://localhost:8090/busStation/voyage/{id}/bus/{busId}
    @RequestMapping(value = "/voyage/{id}/bus/{busId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> changeBusOnVoyage( @PathVariable(value="id") Integer voyageId,
                                                @PathVariable(value="busId") Integer busId) {
        return ResponseEntity.ok(service.changeBusOnVoyage(voyageId, busId));
    }

    //curl -H "Content-type: application/json" -X POST -d '{"place":1, "price":20}' http://localhost:8090/busStation/voyage/{id}/addTicket
    @RequestMapping(value = "/voyage/{id}/addTicket", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addTicketOnVoyage( @PathVariable(value="id") Integer voyageId,
                                                @RequestBody Ticket ticket) {
        Set<Ticket> tickets = new HashSet<>();
        tickets.add(ticket);
        return ResponseEntity.ok(service.addTicketsOnVoyage(voyageId, tickets));
    }

    //curl -H "Content-type: application/json" -X POST -d '[{"place":1, "price":20}, {"place":2, "price":20}]' http://localhost:8090/busStation/voyage/{id}/addTickets
    @RequestMapping(value = "/voyage/{id}/addTickets", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?>  addTicketsOnVoyage( @PathVariable(value="id") Integer voyageId,
                                                  @RequestBody ArrayList<Ticket> tickets) {

        Set<Ticket> ticketSet = new HashSet<>(tickets);
        return ResponseEntity.ok(service.addTicketsOnVoyage(voyageId, ticketSet));
    }

    //curl -H "Content-type: application/json" -X POST http://localhost:8090/busStation/voyage/{id}/ticket/{ticketId}
    @RequestMapping(value = "/voyage/{id}/ticket/{ticketId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?>  sellTicket( @PathVariable(value="id") Integer voyageId,
                                          @PathVariable(value="ticketId") Integer ticketId) {
        return ResponseEntity.ok(service.sellTicket(voyageId, ticketId));
    }

    //curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/voyage/{id}
    @RequestMapping(value = "/voyage/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findOneVoyage(@PathVariable(value="id") Integer id) {
        return ResponseEntity.ok(service.findOneVoyage(id));
    }

    //curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/findAllVoyages
    @RequestMapping(value = "/findAllVoyages", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findAllVoyages() {
        return ResponseEntity.ok(service.findAllVoyages());
    }

    @Autowired
    public void setService(VoyageService service) {
        this.service = service;
    }
}
