package com.mateuswmachado.votesystem.service;

import com.mateuswmachado.votesystem.dto.AssociateDTO;
import com.mateuswmachado.votesystem.model.Associate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public interface AssociateService {

    List<AssociateDTO> getAllAssociates();

    ResponseEntity<Associate> getAssociateById(@PathVariable Long id);

    ResponseEntity<String> saveAssociate(@RequestBody Associate associate, UriComponentsBuilder uriBuilder);

    ResponseEntity<String> deleteAssociateById(@PathVariable Long id);
}
