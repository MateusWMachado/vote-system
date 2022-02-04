package com.mateuswmachado.votesystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ScheduleDTO {

    private Long id;
    private int yesVote;
    private int noVote;
    @NotBlank(message = "Field cannot be empty")
    private String subject;
    @NotNull(message = "Field cannot be null")
    private List<AssociateDTO> votes;
    @JsonIgnore
    private VoteSessionDTO voteSession;
}
