package com.example.ticket.service;

import static org.mockito.Mockito.*;

import com.example.ticket.client.EventClient;
import com.example.ticket.client.NotificationClient;
import com.example.ticket.client.PaymentClient;
import com.example.ticket.entity.Ticket;
import com.example.ticket.repo.TicketRepository;
import com.example.ticket.requestdto.NotificationRequest;
import com.example.ticket.requestdto.PaymentRequest;
import com.example.ticket.responsedto.EventDto;
import com.example.ticket.responsedto.PaymentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

class TicketServiceTest {

    @Mock
    private EventClient eventClient;

    @Mock
    private NotificationClient notificationClient;

    @Mock
    private PaymentClient paymentClient;

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void bookTicket_success() {
        EventDto event = new EventDto();
        event.setId(1);
        event.setAvailableTickets(10);
        when(eventClient.getEventById(1L)).thenReturn(event);

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setSuccess(true);
        when(paymentClient.processPayment(any(PaymentRequest.class))).thenReturn(paymentResponse);

        Ticket ticket = new Ticket();
        ticket.setId(1);
        ticket.setEventId(1L);
        ticket.setNumberOfTickets(2);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket result = ticketService.bookTicket(ticket);
        assertNotNull(result);
        verify(notificationClient, times(1)).sendNotification(any(NotificationRequest.class));
    }

    @Test
    void bookTicket_eventNotFound() {
        when(eventClient.getEventById(1L)).thenReturn(null);

        Ticket ticket = new Ticket();
        ticket.setEventId(1L);

        assertThrows(ResponseStatusException.class, () -> ticketService.bookTicket(ticket));
    }

    @Test
    void bookTicket_notEnoughTickets() {
        EventDto event = new EventDto();
        event.setId(1);
        event.setAvailableTickets(1);
        when(eventClient.getEventById(1L)).thenReturn(event);

        Ticket ticket = new Ticket();
        ticket.setEventId(1L);
        ticket.setNumberOfTickets(2);

        assertThrows(ResponseStatusException.class, () -> ticketService.bookTicket(ticket));
    }
}
