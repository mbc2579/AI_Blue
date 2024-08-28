package com.blue.auth.domain;

public enum UserRoleEnum {
    USER(Authority.USER),
    OWNER(Authority.OWNER),
    ADMIN(Authority.ADMIN),
    MASTER(Authority.MASTER);

    private final String authority;

    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String USER = "USER";
        public static final String OWNER = "OWNER";
        public static final String ADMIN = "ADMIN";
        public static final String MASTER = "MASTER";
    }
}
