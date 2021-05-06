package com.slisnychyi.controller;

import com.slisnychyi.TestUtils;
import com.slisnychyi.model.Event;
import com.slisnychyi.model.EventRequest;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import java.net.MalformedURLException;
import java.net.URL;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.*;

@MicronautTest
class EventControllerTest {
    private static final String URI_PATH = "/api/v1/event";
    @Inject
    private EmbeddedServer server;

    @Test
    public void should_throwUnauthorized_whenUnknownUser() throws MalformedURLException {
        HttpClient client = HttpClient.create(new URL("http://" + server.getHost() + ":" + server.getPort()));

        StepVerifier.create(client
                .retrieve(HttpRequest.GET(URI_PATH).basicAuth("unknown", "unknown")))
                .verifyError(HttpClientResponseException.class);
    }

    @Test
    public void should_throwBadRequest_whenInvalidEventDuration() throws MalformedURLException {
        HttpClient client = HttpClient.create(new URL("http://" + server.getHost() + ":" + server.getPort()));

        StepVerifier.create(client
                .retrieve(HttpRequest.POST(URI_PATH, new EventRequest("wash dishes", -10)).basicAuth("test", "test")))
                .expectErrorSatisfies(throwable -> {
                    String message = throwable.getMessage();
                    assertThat(message).isEqualTo("request.duration: must be greater than or equal to 0");
                })
                .verify();
    }

    @Test
    public void should_saveUserEvent_whenValidEvent() throws MalformedURLException {
        HttpClient client = HttpClient.create(new URL("http://" + server.getHost() + ":" + server.getPort()));
        EventRequest request = new EventRequest("wash dishes", 10);

        StepVerifier.create(client
                .retrieve(HttpRequest.POST(URI_PATH, request).basicAuth("test", "test")))
                .assertNext(e -> assertThat(e).isNotEmpty())
                .verifyComplete();

        StepVerifier.create(client
                .retrieve(HttpRequest.GET(URI_PATH).basicAuth("test", "test")))
                .assertNext(e -> assertThat(TestUtils.readEvents(e)).extracting(Event::getTask)
                        .containsExactly("wash dishes"))
                .verifyComplete();
    }

}