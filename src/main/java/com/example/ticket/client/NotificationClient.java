package com.example.ticket.client;


import com.example.ticket.requestdto.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-ms", url = "http://localhost:8082/api/v1/notifications")
public interface NotificationClient {

    @PostMapping("/send")
    void sendNotification(@RequestBody NotificationRequest notificationRequest);
}
