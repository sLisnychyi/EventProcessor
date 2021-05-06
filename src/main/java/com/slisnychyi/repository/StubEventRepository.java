package com.slisnychyi.repository;

import com.slisnychyi.model.Event;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Singleton
public class StubEventRepository implements EventRepository {
    private final List<Event> events;

    public StubEventRepository() {
        this.events = new ArrayList<>();
    }

    @Override
    public Mono<String> saveEvent(Event event) {
        return Mono.just(event)
                .map(e -> {
                    events.add(e);
                    return e.getUuid();
                });
    }

    @Override
    public Flux<Event> getEvents() {
        return Flux.fromIterable(events);
    }

    @Override
    public Flux<Event> getUserEvents(String username) {
        return Flux.fromIterable(events)
                .filter(e -> e.getUsername().equals(username));
    }
}
