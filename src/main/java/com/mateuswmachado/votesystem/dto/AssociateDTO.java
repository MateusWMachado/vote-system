package com.mateuswmachado.votesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssociateDTO {

    private Long id;
    @CPF
    @NotBlank(message = "Field cannot be empty")
    private String cpf;
}
