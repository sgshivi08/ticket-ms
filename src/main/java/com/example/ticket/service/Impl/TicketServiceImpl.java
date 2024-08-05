package com.example.ticket.service.Impl;

import com.example.ticket.client.EventClient;
import com.example.ticket.client.NotificationClient;
import com.example.ticket.client.PaymentClient;
import com.example.ticket.entity.Ticket;
import com.example.ticket.repo.TicketRepository;
import com.example.ticket.requestdto.NotificationRequest;
import com.example.ticket.requestdto.PaymentRequest;
import com.example.ticket.responsedto.EventDto;
import com.example.ticket.responsedto.PaymentResponse;
import com.example.ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EventClient eventClient;

    @Autowired
    private NotificationClient notificationClient;

    @Autowired
    private PaymentClient paymentClient;


    @Override
    public Ticket bookTicket(Ticket ticket) {
        try {
            // Check event availability by calling Event Service
            EventDto event = eventClient.getEventDetailsBId(ticket.getEventId());

            if (event == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
            }

            if (event.getAvailableTickets() < ticket.getNumberOfTickets()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough tickets available");
            }
            // Prepare payment request
            Ticket savedTicket = ticketRepository.save(ticket);
            processPayment(savedTicket);

            // Reduce available tickets
            event.setAvailableTickets(event.getAvailableTickets() - ticket.getNumberOfTickets());
            eventClient.manageEvent(event);

            // update the ticket booking
            Ticket updateTicket = ticketRepository.save(ticket);

            // Send booking notification
            sendBookingNotification(event, updateTicket);

            return updateTicket;

        }  catch (Exception e) {
            // Handle other exceptions
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred during ticket booking");
        }
    }

    private void processPayment(Ticket ticket) {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setTicketId(ticket.getId());
        paymentRequest.setAmount(ticket.getPaymentAmount());
        paymentRequest.setPaymentMethod("credit_card"); // Use appropriate payment method

        // Process payment
        PaymentResponse paymentResponse = paymentClient.processPayment(paymentRequest);

        if (!paymentResponse.isSuccess()) {
            throw new ResponseStatusException(HttpStatus.PAYMENT_REQUIRED, "Payment failed: " + paymentResponse.getMessage());
        }
    }
    @Override
    public void cancelTicket(Integer ticketId) {
        try {
            // Retrieve the ticket
            Ticket ticket = ticketRepository.findById(ticketId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));

            // Retrieve the event
            EventDto event = eventClient.getEventDetailsBId(ticket.getEventId());
            if (event == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
            }

            // Restore available tickets
            event.setAvailableTickets(event.getAvailableTickets() + ticket.getNumberOfTickets());
            eventClient.manageEvent(event);

            // Delete the ticket
            ticketRepository.deleteById(ticketId);

            // Send cancellation notification
            sendBookingNotification(event,ticket);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred during ticket cancellation", e);
        }
    }

    public boolean checkAvailability(Integer eventId, int numberOfTickets) {
        try {
            // Check event availability
            EventDto event = eventClient.getEventDetailsBId(eventId);
            if (event == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
            }

            return event.getAvailableTickets() >= numberOfTickets;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while checking availability", e);
        }
    }




    private void sendBookingNotification(EventDto event, Ticket ticket) {
        NotificationRequest notification = new NotificationRequest();
        notification.setTicketId(ticket.getId());
        notification.setEventName(event.getEventName());
        notification.setEventDate(event.getEventDate());
        notification.setEventLocation(event.getEventLocation());
        notification.setUserName(ticket.getUserName());
        notification.setTicketType(ticket.getTicketType());
        notification.setNumberOfTickets(ticket.getNumberOfTickets());
        notification.setPaymentAmount(ticket.getPaymentAmount());
        notification.setUserEmail("sgshivi08@gmail.com");
        notificationClient.sendNotification(notification);
    }
}
