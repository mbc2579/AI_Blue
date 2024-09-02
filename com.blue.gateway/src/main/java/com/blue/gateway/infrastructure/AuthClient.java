package com.blue.gateway.infrastructure;

import com.blue.gateway.AuthService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth")
public interface AuthClient extends AuthService {

    @Cacheable(cacheNames = "userRoleCache", key = "args[0]")
    @GetMapping("/api/auth/authority")
    String getAuthority(@RequestParam(value = "userName") String userName);
}
