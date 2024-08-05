package com.example.ticket.requestdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
    private Integer ticketId;
    private String eventName;
    private LocalDate eventDate;
    private String eventLocation;
    private String userName;
    private String ticketType;
    private int numberOfTickets;
    private double paymentAmount;
    private String userEmail;

}
