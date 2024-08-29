package com.blue.gateway.infrastructure;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth")
public interface AuthClient {

    @Cacheable(cacheNames = "userRoleCache", key = "args[0]")
    @GetMapping("/auth/authority")
    UserAuthorityResDto getAuthority(@RequestParam String userName);
}
