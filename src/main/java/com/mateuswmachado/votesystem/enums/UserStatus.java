package com.mateuswmachado.votesystem.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
    UNABLE_TO_VOTE("UNABLE_TO_VOTE"),
    ABLE_TO_VOTE("ABLE_TO_VOTE");

    private final String status;

    UserStatus(String status) {
        this.status = status;
    }
}
