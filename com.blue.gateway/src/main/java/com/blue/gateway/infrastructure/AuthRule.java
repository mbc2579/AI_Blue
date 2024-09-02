package com.blue.gateway.infrastructure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import java.util.Set;

@Getter
@Setter
public class AuthRule {
    private String endpointPattern;
    private Set<HttpMethod> deniedMethods;

    public AuthRule(String endpointPattern, Set<HttpMethod> deniedMethods) {
        this.endpointPattern = endpointPattern;
        this.deniedMethods = deniedMethods;
    }
}
