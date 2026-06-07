package com.charita.event_platform.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "events")
public class Event {

    @Id
    private String id;

    private String title;
    private String description;
    private String location;
    private Date eventDate;

    private String organizerEmail;

    private int maxCapacity;
    private int registeredCount;

    private Date createdAt;

    private   List<String> attendeeEmails;
}