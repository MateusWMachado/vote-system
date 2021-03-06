package com.mateuswmachado.votesystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidVoteException extends RuntimeException{

    public InvalidVoteException(String message) {
        super(message);
    }
}
