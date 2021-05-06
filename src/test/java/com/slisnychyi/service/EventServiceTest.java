package com.slisnychyi.service;

import com.slisnychyi.model.Event;
import com.slisnychyi.model.EventRequest;
import com.slisnychyi.model.EventStatistic;
import com.slisnychyi.repository.StubEventRepository;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;
import reactor.util.function.Tuples;

import static org.assertj.core.api.Assertions.*;

class EventServiceTest {
    private static final String USER_NAME = "username";
    private static final String ANOTHER_USER_NAME = "another_username";
    private static final String TASK_NAME = "buy food";

    private EventService eventService = new EventService(new StubEventRepository());

    @Test
    public void should_getUserEvents_whenPreviouslyStoredEvents() {
        StepVerifier.create(eventService.saveEvent(USER_NAME, new EventRequest(TASK_NAME, 200)))
                .assertNext(e -> assertThat(e).isNotEmpty())
                .verifyComplete();

        StepVerifier.create(eventService.getEvents(USER_NAME))
                .assertNext(e ->
                        assertThat(e).extracting(Event::getTask)
                                .isEqualTo(TASK_NAME))
                .verifyComplete();
    }

    @Test
    public void should_calculateEventStatistic_whenPreviouslyStoredEvents() {
        Stream.of(Tuples.of("play football", 10), Tuples.of("do homework", 20))
                .forEach(tuple2 -> {
                    StepVerifier.create(eventService.saveEvent(USER_NAME, new EventRequest(tuple2.getT1(), tuple2.getT2())))
                            .assertNext(e -> assertThat(e).isNotEmpty())
                            .verifyComplete();
                });

        Stream.of(Tuples.of("play chess", 5))
                .forEach(tuple2 -> {

                    StepVerifier.create(eventService.saveEvent(ANOTHER_USER_NAME, new EventRequest(tuple2.getT1(), tuple2.getT2())))
                            .assertNext(e -> assertThat(e).isNotEmpty())
                            .verifyComplete();
                });

        StepVerifier.create(eventService.getEventsStatistics())
                .expectNext(new EventStatistic(USER_NAME, 2, 30), new EventStatistic(ANOTHER_USER_NAME, 1, 5))
                .verifyComplete();
    }

    @Test
    public void should_calculateEventStatistic_whenNoEventsExists() {
        StepVerifier.create(eventService.getEventsStatistics())
                .expectNextCount(0)
                .verifyComplete();
    }


}