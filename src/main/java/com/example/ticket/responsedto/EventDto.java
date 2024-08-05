package com.example.ticket.responsedto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {

    @JsonProperty("event_id")
    private Integer id;

    @JsonProperty("event_name")
    private String eventName;

    @JsonProperty("event_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate eventDate;

    @JsonProperty("event_time")
    private LocalTime eventTime;

    @JsonProperty("event_location")
    private String eventLocation;

    @JsonProperty("available_tickets")
    private Integer availableTickets;


}
