package com.example.ticket.client;

import com.example.ticket.requestdto.PaymentRequest;
import com.example.ticket.responsedto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "http://localhost:8083/api/v1/payments") // URL to the payment service
public interface PaymentClient {

    @PostMapping("/process")
    PaymentResponse processPayment(@RequestBody PaymentRequest paymentRequest);
}
