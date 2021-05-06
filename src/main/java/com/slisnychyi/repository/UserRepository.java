package com.slisnychyi.repository;

import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<String> getUserPassword(String username);

    Mono<String> getUserRole(String username);
}
