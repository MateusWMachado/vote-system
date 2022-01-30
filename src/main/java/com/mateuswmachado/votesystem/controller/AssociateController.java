package com.mateuswmachado.votesystem.controller;

import com.mateuswmachado.votesystem.dto.AssociateDTO;
import com.mateuswmachado.votesystem.model.Associate;
import com.mateuswmachado.votesystem.service.AssociateService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/associate")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AssociateController {

    private AssociateService service;
    private ModelMapper mapper;

    @GetMapping
    private List<AssociateDTO> getAll() {
        return service.getAllAssociates();
    }

    @GetMapping("/{id}")
    private ResponseEntity<Associate> getById(@PathVariable Long id) {
        return service.getAssociateById(id);
    }

    @PostMapping
    private ResponseEntity<String> save(@RequestBody Associate associate, UriComponentsBuilder uriBuilder) {
        return service.saveAssociate(associate, uriBuilder);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> delete(@PathVariable Long id) {
        return service.deleteAssociateById(id);
    }
}
