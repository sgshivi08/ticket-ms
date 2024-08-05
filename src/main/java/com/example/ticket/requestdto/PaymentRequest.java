package com.example.ticket.requestdto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentRequest {

    // Getters and setters
    private Integer ticketId;
    private double amount;
    private String paymentMethod; // E.g., "credit_card", "paypal", etc.

}
