package com.example.ticket.controller;

import com.example.ticket.entity.Ticket;
import com.example.ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vi/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/book")
    public ResponseEntity<Ticket> bookTicket(@RequestBody Ticket ticket) {
        Ticket bookedTicket = ticketService.bookTicket(ticket);
        if (bookedTicket != null) {
            return ResponseEntity.ok(bookedTicket);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Handle booking failure scenario
    }

    @DeleteMapping("/cancel/{ticketId}")
    public ResponseEntity<String> cancelTicket(@PathVariable Integer ticketId) {
        try {
            ticketService.cancelTicket(ticketId);
            return ResponseEntity.ok("ticket cancelled successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error cancelling ticket: " + e.getMessage());
        }
    }

    @GetMapping("/availability")
    public ResponseEntity<Boolean> checkAvailability(
            @RequestParam Integer eventId,
            @RequestParam int numberOfTickets) {
        boolean isAvailable = ticketService.checkAvailability(eventId, numberOfTickets);
        return new ResponseEntity<>(isAvailable, HttpStatus.OK);
    }
}
