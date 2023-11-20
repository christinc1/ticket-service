package com.chris.learn.springcloud.ticketservice.controllers;

import com.chris.learn.springcloud.ticketservice.dto.TicketReqDto;
import com.chris.learn.springcloud.ticketservice.model.Ticket;
import com.chris.learn.springcloud.ticketservice.repository.TicketRepository;
import com.chris.learn.springcloud.ticketservice.util.RestClientOperations;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/tickets")
public class TicketRestController {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    RestClientOperations restClientOperations;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Ticket ticket) {
        return ResponseEntity.ok(ticketRepository.save(ticket));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTicket(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ticketRepository.findById(id));
    }

    @PostMapping("/total-price")
    public ResponseEntity<?> getTicketPrice(@RequestBody TicketReqDto ticketReqDto) throws JSONException {
        Double pricePerTicket = restClientOperations.fetchTicketPrice(ticketReqDto.getId());
        if(pricePerTicket == null){
            return ResponseEntity.ok("No price found for the id");
        }
        Optional<Ticket> optionalTicket = ticketRepository.findById(ticketReqDto.getId());
        Ticket ticket = optionalTicket.get();
        ticket.setCount(ticketReqDto.getCount());
        ticket.setTotalPrice(BigDecimal.valueOf(pricePerTicket * ticketReqDto.getCount()));
        return ResponseEntity.ok(ticketRepository.save(ticket));
    }
}
