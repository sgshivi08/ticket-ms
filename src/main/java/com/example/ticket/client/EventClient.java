package com.example.ticket.client;

import com.example.ticket.responsedto.EventDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "eventClient", url = "http://localhost:8080/api/v1/event")
public interface EventClient {

    @GetMapping("/getById/{event-id}")
    EventDto getEventDetailsById(@PathVariable("event-id") Integer id);

    @PutMapping("/manage")
    void manageEvent(@RequestBody EventDto event);

}
