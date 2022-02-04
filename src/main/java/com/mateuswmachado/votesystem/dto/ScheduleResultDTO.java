package com.mateuswmachado.votesystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ScheduleResultDTO {

    private Long id;
    private int yesVote;
    private int noVote;
    private String subject;
    private String result;
    private List<AssociateDTO> votes;
}

