package com.slisnychyi.service;

import com.slisnychyi.model.Event;
import com.slisnychyi.model.EventRequest;
import com.slisnychyi.model.EventStatistic;
import com.slisnychyi.repository.EventRepository;
import java.time.Duration;
import java.util.Comparator;
import java.util.UUID;
import java.util.function.BinaryOperator;
import javax.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Singleton
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Mono<String> saveEvent(String username, EventRequest request) {
        String uuid = UUID.randomUUID().toString();
        Event event = new Event(uuid, username, request);
        return eventRepository.saveEvent(event);
    }

    public Flux<Event> getEvents(String username) {
        return eventRepository.getUserEvents(username);
    }

    public Flux<EventStatistic> getEventsStatistics() {
        return eventRepository.getEvents()
                .groupBy(Event::getUsername)
                .flatMap(Flux::collectList)
                .map(e -> {
                    String username = e.get(0).getUsername();
                    int totalDuration = e.stream().map(Event::getDuration)
                            .reduce(Integer::sum).orElse(0);
                   return new EventStatistic(username, e.size(), totalDuration);
                })
                .sort((o1, o2) -> o2.getTotalTasks() - o1.getTotalTasks());
    }
}
