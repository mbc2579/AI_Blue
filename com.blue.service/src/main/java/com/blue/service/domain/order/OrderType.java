package com.blue.service.domain.order;

public enum OrderType {
    ONLINE(0),
    OFFLINE(1);

    private int type;

    OrderType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
