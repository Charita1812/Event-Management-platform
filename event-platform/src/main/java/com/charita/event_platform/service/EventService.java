package com.charita.event_platform.service;

import com.charita.event_platform.dto.EventRequest;
import com.charita.event_platform.model.Event;
import com.charita.event_platform.model.Role;
import com.charita.event_platform.model.User;
import com.charita.event_platform.repository.EventRepository;
import com.charita.event_platform.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public EventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository=userRepository;
    }

    public Event createEvent(
            EventRequest request,
            String organizerEmail) {

        User user = userRepository.findByEmail(organizerEmail);

        if (user.getRole() != Role.ORGANISER) {
            throw new RuntimeException(
                    "Only organisers can create events"
            );
        }

        Event event = new Event();

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setLocation(request.getLocation());
        event.setEventDate(request.getEventDate());

        event.setMaxCapacity(request.getMaxCapacity());

        event.setOrganizerEmail(organizerEmail);
        event.setAttendeeEmails(new ArrayList<>());

        event.setRegisteredCount(0);

        event.setCreatedAt(new Date());

        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event registerForEvent(
            String eventId,
            String email) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new RuntimeException("Event not found"));

        if(event.getAttendeeEmails() == null) {
            event.setAttendeeEmails(new ArrayList<>());
        }

        if(event.getAttendeeEmails().contains(email)) {
            throw new RuntimeException("Already registered");
        }

        if(event.getRegisteredCount() >= event.getMaxCapacity()) {
            throw new RuntimeException("Event is full");
        }

        event.getAttendeeEmails().add(email);

        event.setRegisteredCount(
                event.getRegisteredCount() + 1
        );

        return eventRepository.save(event);
    }

    public List<Event> getMyEvents(String email) {
        return eventRepository
                .findByAttendeeEmailsContaining(email);
    }

    public void deleteEvent(String eventId, String email) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new RuntimeException("Event not found"));

        if (!event.getOrganizerEmail().equals(email)) {
            throw new RuntimeException("Not authorized");
        }

        eventRepository.delete(event);
    }

    public Event updateEvent(
            String eventId,
            EventRequest request,
            String email) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new RuntimeException("Event not found"));

        if (!event.getOrganizerEmail().equals(email)) {
            throw new RuntimeException("Not authorized");
        }

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setLocation(request.getLocation());
        event.setEventDate(request.getEventDate());
        event.setMaxCapacity(request.getMaxCapacity());

        return eventRepository.save(event);
    }

    public List<Event> getMyCreatedEvents(String email) {

        return eventRepository.findByOrganizerEmail(email);
    }
}