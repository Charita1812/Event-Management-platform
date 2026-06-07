package com.charita.event_platform.controller;

import com.charita.event_platform.dto.EventRequest;
import com.charita.event_platform.model.Event;
import com.charita.event_platform.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/create")
    public Event createEvent(
            @Valid @RequestBody EventRequest request) {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return eventService.createEvent(
                request,
                email
        );
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping("/{eventId}/register")
    public Event registerForEvent(
            @PathVariable String eventId) {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return eventService.registerForEvent(
                eventId,
                email
        );
    }

    @GetMapping("/my-events")
    public List<Event> getMyEvents() {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return eventService.getMyEvents(email);
    }

    @DeleteMapping("/{eventId}")
    public String deleteEvent(
            @PathVariable String eventId) {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        eventService.deleteEvent(
                eventId,
                email
        );

        return "Event deleted successfully";
    }

    @GetMapping("/my-created-events")
    public ResponseEntity<List<Event>> getMyCreatedEvents() {

        String email =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

        return ResponseEntity.ok(
                eventService.getMyCreatedEvents(email)
        );
    }

    @PutMapping("/{eventId}")
    public Event updateEvent(
            @PathVariable String eventId,
            @Valid @RequestBody EventRequest request) {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return eventService.updateEvent(
                eventId,
                request,
                email
        );
    }
}