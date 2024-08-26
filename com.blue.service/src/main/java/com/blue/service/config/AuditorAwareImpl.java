package com.blue.service.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

class AuditorAwareImpl implements AuditorAware<String> {

        @Override
        public Optional<String> getCurrentAuditor() {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String modifiedBy = request.getHeader("X-User-Name");
            if (modifiedBy == null) {
                throw new SecurityException("X-User-Name is required");
            }
            return Optional.of(modifiedBy);
        }
    }