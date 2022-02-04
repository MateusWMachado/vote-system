package com.mateuswmachado.votesystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class AssociatedNotFoundException extends Exception {

    public AssociatedNotFoundException(String message) {
        super(message);
    }

}