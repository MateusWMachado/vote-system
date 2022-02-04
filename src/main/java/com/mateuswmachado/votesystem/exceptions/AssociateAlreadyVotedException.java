package com.mateuswmachado.votesystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class AssociateAlreadyVotedException extends RuntimeException {

    public AssociateAlreadyVotedException(String message) {
        super(message);
    }

}