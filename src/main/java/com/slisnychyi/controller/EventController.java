package com.slisnychyi.controller;

import com.slisnychyi.model.Event;
import com.slisnychyi.model.EventRequest;
import com.slisnychyi.model.EventStatistic;
import com.slisnychyi.service.EventService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.validation.Validated;
import java.security.Principal;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller("api/v1/event")
@Secured(SecurityRule.IS_AUTHENTICATED)
@Validated
public class EventController {
    private static final Logger LOG =
            LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Post
    @Secured({"USER", "ADMIN"})
    Mono<String> saveEvent(Principal principal, @Body @Valid EventRequest request) {
        String username = principal.getName();
        LOG.info("Received request to save user[{}] event", username);
        return eventService.saveEvent(username, request);
    }

    @Get
    @Secured({"USER", "ADMIN"})
    Flux<Event> getEvents(Principal principal) {
        String username = principal.getName();
        LOG.info("Received request to get user[{}] events", username);
        return eventService.getEvents(username);
    }

    @Get("/statistics")
    @Secured("ADMIN")
    Flux<EventStatistic> getEventStatistics() {
        LOG.info("Received request to get user event statistics");
        return eventService.getEventsStatistics();
    }
}
