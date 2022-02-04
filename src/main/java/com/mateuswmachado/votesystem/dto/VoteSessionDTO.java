package com.mateuswmachado.votesystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteSessionDTO {

    private Long id;
    private ScheduleDTO schedule;
    private LocalDateTime endTime;
    private LocalDateTime startTime;
    private Integer duration;
    @JsonIgnore
    private Long idSchedule;
}
