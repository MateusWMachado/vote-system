package com.mateuswmachado.votesystem.service;

import com.mateuswmachado.votesystem.dto.AssociateDTO;
import com.mateuswmachado.votesystem.exceptions.AssociatedNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/** Interface that represents an operations contract on Associate
 *
 * @author Mateus W. Machado
 * @since 02/02/2022
 * @version 1.0.0
 */

public interface AssociateService {

    List<AssociateDTO> getAllAssociates();

    AssociateDTO findAssociateById(Long id) throws AssociatedNotFoundException;

    ResponseEntity<String> saveAssociate(AssociateDTO associateDTO, UriComponentsBuilder uriBuilder);

    AssociateDTO findByCpf(String cpf);
}
