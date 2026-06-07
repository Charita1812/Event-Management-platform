package com.charita.event_platform.repository;

import com.charita.event_platform.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventRepository
        extends MongoRepository<Event, String> {

    List<Event> findByAttendeeEmailsContaining(String email);
    List<Event> findByOrganizerEmail(String organizerEmail);

}