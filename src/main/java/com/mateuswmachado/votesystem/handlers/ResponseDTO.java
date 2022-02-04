package com.mateuswmachado.votesystem.handlers;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO {

    private String field;
    private String error;

}

