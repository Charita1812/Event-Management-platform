package com.charita.event_platform.service;

import com.charita.event_platform.model.Event;
import com.charita.event_platform.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    void shouldThrowWhenEventNotFound() {

        String eventId = "123";
        String email = "test@gmail.com";

        org.mockito.Mockito.when(
                eventRepository.findById(eventId)
        ).thenReturn(java.util.Optional.empty());

        RuntimeException exception =
                org.junit.jupiter.api.Assertions.assertThrows(
                        RuntimeException.class,
                        () -> eventService.registerForEvent(
                                eventId,
                                email
                        )
                );

        org.junit.jupiter.api.Assertions.assertEquals(
                "Event not found",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowWhenAlreadyRegistered() {

        Event event = new Event();

        event.setId("1");
        event.setMaxCapacity(100);
        event.setRegisteredCount(1);

        event.setAttendeeEmails(
                new java.util.ArrayList<>(
                        java.util.Arrays.asList(
                                "test@gmail.com"
                        )
                )
        );

        org.mockito.Mockito.when(
                eventRepository.findById("1")
        ).thenReturn(
                java.util.Optional.of(event)
        );

        RuntimeException exception =
                org.junit.jupiter.api.Assertions.assertThrows(
                        RuntimeException.class,
                        () -> eventService.registerForEvent(
                                "1",
                                "test@gmail.com"
                        )
                );

        org.junit.jupiter.api.Assertions.assertEquals(
                "Already registered",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowWhenEventIsFull() {

        Event event = new Event();

        event.setId("1");
        event.setMaxCapacity(100);
        event.setRegisteredCount(100);

        event.setAttendeeEmails(
                new java.util.ArrayList<>()
        );

        org.mockito.Mockito.when(
                eventRepository.findById("1")
        ).thenReturn(
                java.util.Optional.of(event)
        );

        RuntimeException exception =
                org.junit.jupiter.api.Assertions.assertThrows(
                        RuntimeException.class,
                        () -> eventService.registerForEvent(
                                "1",
                                "new@gmail.com"
                        )
                );

        org.junit.jupiter.api.Assertions.assertEquals(
                "Event is full",
                exception.getMessage()
        );
    }

    @Test
    void shouldRegisterSuccessfully() {

        Event event = new Event();

        event.setId("1");
        event.setMaxCapacity(100);
        event.setRegisteredCount(0);

        event.setAttendeeEmails(
                new java.util.ArrayList<>()
        );

        org.mockito.Mockito.when(
                eventRepository.findById("1")
        ).thenReturn(
                java.util.Optional.of(event)
        );

        org.mockito.Mockito.when(
                eventRepository.save(event)
        ).thenReturn(event);

        Event result =
                eventService.registerForEvent(
                        "1",
                        "new@gmail.com"
                );

        org.junit.jupiter.api.Assertions.assertEquals(
                1,
                result.getRegisteredCount()
        );

        org.junit.jupiter.api.Assertions.assertTrue(
                result.getAttendeeEmails()
                        .contains("new@gmail.com")
        );
    }

}