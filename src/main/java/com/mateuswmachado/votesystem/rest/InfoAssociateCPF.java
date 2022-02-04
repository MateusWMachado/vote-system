package com.mateuswmachado.votesystem.rest;

import com.mateuswmachado.votesystem.dto.UserStatusDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/** Validation class to know if the associate's cpf is valid
 *
 * @author Mateus W. Machado
 * @since 03/02/2022
 * @version 1.0.0
 */

@Slf4j
@Component
public class InfoAssociateCPF {

    public UserStatusDTO validateAssociateCPF(String cpf) {
        log.info("InfoAssociateCPF.validateAssociateCPF - service: https://user-info.herokuapp.com/users/[{}]", cpf);

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://user-info.herokuapp.com/users/";
        ResponseEntity<UserStatusDTO> response = restTemplate.getForEntity(url + cpf, UserStatusDTO.class);

        log.info("InfoAssociateCPF.validateAssociateCPF - results from service: [{}]", response.getBody());

        return response.getBody();
    }

}
