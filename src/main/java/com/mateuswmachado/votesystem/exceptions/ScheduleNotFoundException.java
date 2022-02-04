package com.mateuswmachado.votesystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ScheduleNotFoundException extends Exception{

    public ScheduleNotFoundException(Long id) {
        super("Schedule not found with id " + id);
    }
}
