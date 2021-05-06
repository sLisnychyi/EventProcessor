package com.slisnychyi.configuration;

import com.slisnychyi.repository.UserRepository;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.UserDetails;
import java.util.List;
import javax.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

@Singleton
public class UserAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepository;

    public UserAuthenticationProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        String username = authenticationRequest.getIdentity().toString();
        return userRepository.getUserPassword(username)
                .zipWith(userRepository.getUserRole(username))
                .flatMap(tuple2 -> {
                    String password = authenticationRequest.getSecret().toString();
                    if (tuple2.getT1().equals(password)) {
                        return Mono.just(new UserDetails(username, List.of(tuple2.getT2())));
                    } else {
                        return Mono.just(new AuthenticationFailed(String.format("Invalid user[%s] password", username)));
                    }
                })
                .switchIfEmpty(Mono.just(new AuthenticationFailed(String.format("No such user[%s] exists.", username))));
    }
}
