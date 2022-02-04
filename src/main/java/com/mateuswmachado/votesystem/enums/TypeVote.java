package com.mateuswmachado.votesystem.enums;

import lombok.Getter;

@Getter
public enum TypeVote {

    YES("YES"),
    NO("NO");

    private final String typeVote;

    TypeVote(String typeVote) {
        this.typeVote = typeVote;
    }
}

