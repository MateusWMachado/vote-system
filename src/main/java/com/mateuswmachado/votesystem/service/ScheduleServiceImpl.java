package com.mateuswmachado.votesystem.service;

import com.mateuswmachado.votesystem.dto.ScheduleDTO;
import com.mateuswmachado.votesystem.dto.ScheduleResultDTO;
import com.mateuswmachado.votesystem.dto.VoteDTO;
import com.mateuswmachado.votesystem.enums.TypeVote;
import com.mateuswmachado.votesystem.exceptions.*;
import com.mateuswmachado.votesystem.model.Schedule;
import com.mateuswmachado.votesystem.repository.ScheduleRepository;
import com.mateuswmachado.votesystem.service.validation.ScheduleValidation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Objects;

/** ScheduleService contract implementation class
 *
 * @author Mateus W. Machado
 * @since 02/02/2022
 * @version 1.0.0
 */

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private ModelMapper mapper;
    private ScheduleRepository scheduleRepository;
    private ScheduleValidation scheduleValidation;

    @Override
    public ResponseEntity<ScheduleDTO> createSchedule(ScheduleDTO scheduleDTO, UriComponentsBuilder uriBuilder) {
        Schedule savedSchedule = scheduleRepository.save(mapper.map(scheduleDTO, Schedule.class));
        URI uri = uriBuilder.path("/api/v1/schedule/{id}").buildAndExpand(savedSchedule.getId()).toUri();

        log.info("ScheduleServiceImpl.createSchedule - create new schedule. results: [{}]", savedSchedule);

        return ResponseEntity.created(uri).body(mapper.map(savedSchedule, ScheduleDTO.class));
    }

    @Override
    @Transactional
    public ScheduleDTO verifyIfScheduleExists(Long id) throws ScheduleNotFoundException{
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ScheduleNotFoundException(id));
        return mapper.map(schedule, ScheduleDTO.class);
    }

    @Override
    public VoteDTO voteSchedule(VoteDTO voteDTO) throws ScheduleNotFoundException {
        ScheduleDTO scheduleDTO = this.verifyIfScheduleExists(voteDTO.getIdSchedule());

        if (Objects.isNull(scheduleDTO.getVoteSession())) {
            throw new ScheduleHasNoVotingSessionException("Schedule has no voting session");
        }

        if(Objects.nonNull(scheduleDTO.getVoteSession().getEndTime()) && scheduleDTO.getVoteSession().getEndTime().isBefore(LocalDateTime.now())) {
            log.info("Schedule session finished");
            throw new ScheduleSessionFinishedException("Schedule session finished");
        }

        scheduleValidation.validateVotes(scheduleDTO.getVotes(), voteDTO.getCpf());

        countVotes(scheduleDTO, voteDTO.getVote());

        scheduleRepository.save(mapper.map(scheduleDTO, Schedule.class));
        log.info("ScheduleServiceImpl.voteSchedule - end. results: [{}]", voteDTO);

        return voteDTO;
    }

    @Override
    public ScheduleResultDTO scheduleResult(Long id) throws ScheduleNotFoundException {
        ScheduleDTO scheduleDTO = this.verifyIfScheduleExists(id);

        if(scheduleDTO.getVoteSession().getEndTime().isAfter(LocalDateTime.now())){
            throw new ScheduleException("Schedule in progress");
        }

        ScheduleResultDTO result = ScheduleResultDTO.builder()
                .id(scheduleDTO.getId())
                .yesVote(scheduleDTO.getYesVote())
                .noVote(scheduleDTO.getNoVote())
                .result(scheduleDTO.getYesVote() > scheduleDTO.getNoVote() ? TypeVote.YES.getTypeVote() : TypeVote.NO.getTypeVote())
                .subject(scheduleDTO.getSubject())
                .votes(scheduleDTO.getVotes())
                .build();

        //resultProducer.send(result);

        log.info("ScheduleServiceImpl.scheduleResult - find schedule. results: [{}]", result);

        return result;
    }

    private void countVotes(ScheduleDTO scheduleDTO, String vote) {
        if (TypeVote.YES.getTypeVote().equals(vote)) {
            scheduleDTO.setYesVote(scheduleDTO.getYesVote() + 1);
        } else if (TypeVote.NO.getTypeVote().equals(vote)) {
            scheduleDTO.setNoVote(scheduleDTO.getNoVote() + 1);
        } else {
            throw new InvalidVoteException("Invalid vote");
        }
    }


}


