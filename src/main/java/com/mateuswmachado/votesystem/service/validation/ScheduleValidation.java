package com.mateuswmachado.votesystem.service.validation;

import com.mateuswmachado.votesystem.dto.AssociateDTO;
import com.mateuswmachado.votesystem.dto.UserStatusDTO;
import com.mateuswmachado.votesystem.enums.UserStatus;
import com.mateuswmachado.votesystem.exceptions.AssociateAlreadyVotedException;
import com.mateuswmachado.votesystem.exceptions.ScheduleException;
import com.mateuswmachado.votesystem.kafka.producer.ResultProducer;
import com.mateuswmachado.votesystem.repository.ScheduleRepository;
import com.mateuswmachado.votesystem.rest.InfoAssociateCPF;
import com.mateuswmachado.votesystem.service.AssociateService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
public class ScheduleValidation {

    private AssociateService associateService;
    private InfoAssociateCPF infoAssociateCPF;

    public void validateVotes(List<AssociateDTO> votes, String cpfAssociate) {
        Optional<AssociateDTO> vote = votes.stream().filter(v -> v.getCpf().equals(cpfAssociate)).findAny();
        if(vote.isPresent()) {
            log.info("Associated already voted");
            throw new AssociateAlreadyVotedException("Associated already voted");
        } else {
            AssociateDTO associateDTO = associateService.findByCpf(cpfAssociate);
            //validateIfAssociateCanVote(cpfAssociate);
            votes.add(associateDTO);
        }
    }

    public void validateIfAssociateCanVote(String cpf)  {
        UserStatusDTO userStatusDTO = infoAssociateCPF.validateAssociateCPF(cpf);
        if(UserStatus.ABLE_TO_VOTE.getStatus().equals(userStatusDTO.getStatus())) {
            log.info("Usuario do cpf {} habilitado para votar", cpf);
        } else if (UserStatus.UNABLE_TO_VOTE.getStatus().equals(userStatusDTO.getStatus())) {
            log.info("O Associado de cpf "+ cpf +", não tem permissao para votar!");
            throw new ScheduleException("O Associado de cpf "+ cpf +", não tem permissao para votar!");
        }
    }
}
