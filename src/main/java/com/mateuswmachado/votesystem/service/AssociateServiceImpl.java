package com.mateuswmachado.votesystem.service;

import com.mateuswmachado.votesystem.dto.AssociateDTO;
import com.mateuswmachado.votesystem.exceptions.AssociatedNotFoundException;
import com.mateuswmachado.votesystem.model.Associate;
import com.mateuswmachado.votesystem.repository.AssociateRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/** AssociateService contract implementation class
 *
 * @author Mateus W. Machado
 * @since 02/02/2022
 * @version 1.0.0
 */

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AssociateServiceImpl implements AssociateService{

    private AssociateRepository repository;
    private ModelMapper mapper;

    @Override
    public List<AssociateDTO> getAllAssociates() {
        List<Associate> all = repository.findAll();

        log.info("AssociateServiceImpl.getAllAssociates - Get All Associates - End [{}] ", all);

        return all.stream().map(associate -> mapper.map(associate, AssociateDTO.class)).collect(Collectors.toList());
    }

    @Override
    public AssociateDTO findAssociateById(Long id) throws AssociatedNotFoundException {
        Associate associate = repository.findById(id)
                .orElseThrow(() -> new AssociatedNotFoundException("Associate not found"));

        return mapper.map(associate, AssociateDTO.class);
    }

    @Override
    public ResponseEntity<String> saveAssociate(AssociateDTO associateDTO, UriComponentsBuilder uriBuilder) {
        Associate savedAssociate = repository.save(mapper.map(associateDTO, Associate.class));
        URI uri = uriBuilder.path("/api/v1/associate/{id}").buildAndExpand(savedAssociate.getId()).toUri();

        log.info("AssociateServiceImpl.saveAssociate - End creating associate - Saved Associate [{}] ", savedAssociate);

        return ResponseEntity.created(uri).body("Associate with id "+ savedAssociate.getId()+ " created");
    }

    @Override
    public AssociateDTO findByCpf(String cpf) {
        Associate associate = repository.findByCpf(cpf).orElseThrow(() -> new EntityNotFoundException("Associate not found"));

        return mapper.map(associate, AssociateDTO.class);
    }
}
