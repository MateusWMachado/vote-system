package com.mateuswmachado.votesystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ScheduleHasNoVotingSessionException extends RuntimeException{

    public ScheduleHasNoVotingSessionException(String message) {
        super(message);
    }
}
