package com.controllers;

import com.services.TicketService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/busStation")
public class TicketController {

    private TicketService service;

    //curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/findAllTickets
    @RequestMapping(value = "/findAllTickets", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findAllTickets() {
        return ResponseEntity.ok(service.findAll());
    }

    //curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/ticket/{id}
    @RequestMapping(value = "/ticket/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findOneTicket(@PathVariable(value="id") Integer id) {
        return ResponseEntity.ok(service.findOne(id));
    }

    @Autowired
    public void setService(TicketService service) {
        this.service = service;
    }
}
