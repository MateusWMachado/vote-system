package com.mateuswmachado.votesystem.service;

import com.mateuswmachado.votesystem.dto.AssociateDTO;
import com.mateuswmachado.votesystem.exceptions.AssociatedNotFoundException;
import com.mateuswmachado.votesystem.model.Associate;
import com.mateuswmachado.votesystem.repository.AssociateRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Associate service test class
 *
 * @author Mateus W. Machado
 * @since 03/02/2022
 * @version 1.0.0
 */

@SpringBootTest
class AssociateServiceImplTest {

    @Autowired
    private AssociateServiceImpl service;

    @Mock
    private AssociateRepository associateRepository;

    @Autowired
    private ModelMapper mapper;

    @DisplayName("Test to get all associates correctly")
    @Test
    void getAllAssociates() {
        Associate a1 = new Associate(1L, "287.538.880-06");
        service.saveAssociate(mapper.map(a1, AssociateDTO.class), UriComponentsBuilder.newInstance());
        List<AssociateDTO> listOfAssociates = service.getAllAssociates();
        assertNotNull(listOfAssociates);
    }

    @DisplayName("Test to get a associate by id")
    @Test
    void findAssociateById() throws AssociatedNotFoundException {
        AssociateDTO associateDTO = service.findAssociateById(1L);
        assertEquals(associateDTO.getId(), 1L);
        assertEquals(associateDTO.getCpf(), "287.538.880-06");
    }

    @DisplayName("Test to throw an exception if it doesn't find an associate by id")
    @Test
    void throwNotFoundAssociateException()  {
        assertThrows(AssociatedNotFoundException.class, () -> service.findAssociateById(0L), "Test");
    }

    @DisplayName("Test create an associate correctly")
    @Test
    void saveAssociate() {
        Associate a1 = new Associate(2L, "890.596.010-33");
        ResponseEntity<String> savedAssociate = service.saveAssociate(mapper.map(a1, AssociateDTO.class), UriComponentsBuilder.newInstance());
        assertEquals(savedAssociate.getStatusCode(), HttpStatus.CREATED);
    }

    @DisplayName("Test get a associate by CPF")
    @Test
    void findByCpf()  {
        AssociateDTO associateDTO = service.findByCpf("287.538.880-06");
        assertEquals(associateDTO.getId(), 1L);
        assertEquals(associateDTO.getCpf(), "287.538.880-06");
    }
}