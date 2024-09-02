package com.blue.auth.domain;

public enum UserRoleEnum {
    CUSTOMER(Authority.CUSTOMER),
    OWNER(Authority.OWNER),
    MANAGER(Authority.MANAGER),
    MASTER(Authority.MASTER);

    private final String authority;

    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String CUSTOMER = "CUSTOMER";
        public static final String OWNER = "OWNER";
        public static final String MANAGER = "MANAGER";
        public static final String MASTER = "MASTER";
    }
}
