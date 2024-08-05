package com.example.ticket.service;

import com.example.ticket.entity.Ticket;
public interface TicketService {

    public Ticket bookTicket(Ticket ticket);

    void cancelTicket(Integer ticketId);

    boolean checkAvailability(Integer eventId, int numberOfTickets);
}
