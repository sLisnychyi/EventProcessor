package com.slisnychyi.repository;

import com.slisnychyi.model.Event;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventRepository {
    Mono<String> saveEvent(Event event);

    Flux<Event> getEvents();

    Flux<Event> getUserEvents(String username);
}
