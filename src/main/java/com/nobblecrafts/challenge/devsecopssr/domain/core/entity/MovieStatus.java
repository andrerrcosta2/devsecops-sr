package com.nobblecrafts.challenge.devsecopssr.domain.core.entity;

public enum MovieStatus {
    WATCHED("watched"),
    WANT_WATCH("want-to-watch"),
    FAVORITE("favorite"),

    NONE("none");

    MovieStatus(String status) {
    }

    public String getStatus() {
        return name();
    }
}
