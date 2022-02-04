package com.mateuswmachado.votesystem.controller;

import com.mateuswmachado.votesystem.dto.AssociateDTO;
import com.mateuswmachado.votesystem.exceptions.AssociatedNotFoundException;
import com.mateuswmachado.votesystem.model.Associate;
import com.mateuswmachado.votesystem.service.AssociateService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

/** Class that represents a controller to handle information from Associates
 *
 * @author Mateus W. Machado
 * @since 02/02/2022
 * @version 1.0.0
 */

@Slf4j
@RestController
@RequestMapping("/api/v1/associate")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AssociateController {

    private AssociateService service;
    private ModelMapper mapper;

    @GetMapping
    @ApiOperation("Get all Associates")
    private List<AssociateDTO> getAll() {
        return service.getAllAssociates();
    }

    @GetMapping("/{id}")
    @ApiOperation("Get a Associate by ID")
    private AssociateDTO getById(@PathVariable Long id) throws AssociatedNotFoundException {
        return service.findAssociateById(id);
    }

    @PostMapping
    @Transactional
    @ApiOperation("Create an Associate")
    private ResponseEntity<String> create(@RequestBody @Valid AssociateDTO associateDTO, UriComponentsBuilder uriBuilder) {

        log.info("AssociateController.create - start creating associate - associate info [{}] ", associateDTO);

        return service.saveAssociate(associateDTO, uriBuilder);
    }
}
