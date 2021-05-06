package com.slisnychyi.repository;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.convert.format.MapFormat;
import java.util.Map;
import reactor.core.publisher.Mono;

@ConfigurationProperties("credentials")
public class StubUserRepository implements UserRepository {
    @MapFormat
    Map<String, String> users;
    @MapFormat
    Map<String, String> roles;

    @Override
    public Mono<String> getUserPassword(String username) {
        return Mono.justOrEmpty(users.get(username));
    }

    @Override
    public Mono<String> getUserRole(String username) {
        return Mono.justOrEmpty(roles.get(username));
    }
}
