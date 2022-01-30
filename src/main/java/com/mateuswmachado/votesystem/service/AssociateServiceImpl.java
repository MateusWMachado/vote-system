package com.mateuswmachado.votesystem.service;

import com.mateuswmachado.votesystem.dto.AssociateDTO;
import com.mateuswmachado.votesystem.model.Associate;
import com.mateuswmachado.votesystem.repository.AssociateRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AssociateServiceImpl implements AssociateService{

    private AssociateRepository repository;
    private ModelMapper mapper;

    @Override
    public List<AssociateDTO> getAllAssociates() {
        List<Associate> all = repository.findAll();
        return all.stream().map(associate -> mapper.map(associate, AssociateDTO.class)).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<Associate> getAssociateById(Long id) {
        Optional<Associate> byId = repository.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(byId.get());
    }

    @Override
    public ResponseEntity<String> saveAssociate(Associate associate, UriComponentsBuilder uriBuilder) {
        Associate savedAssociate = repository.save(associate);
        URI uri = uriBuilder.path("/api/v1/associate/{id}").buildAndExpand(savedAssociate.getId()).toUri();
        return ResponseEntity.created(uri).body("Associate with id "+ savedAssociate.getId()+ " created");
    }

    @Override
    public ResponseEntity<String> deleteAssociateById(Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
