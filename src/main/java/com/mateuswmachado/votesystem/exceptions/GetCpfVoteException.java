package com.mateuswmachado.votesystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class GetCpfVoteException extends RuntimeException {

    public GetCpfVoteException(String message) {
        super(message);
    }

}
