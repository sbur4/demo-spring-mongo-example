package org.example.security;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // Just an example, adapt it according to your security setup
        return Optional.of("username");
    }
}
