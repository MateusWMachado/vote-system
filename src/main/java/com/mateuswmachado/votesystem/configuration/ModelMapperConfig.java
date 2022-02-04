package com.mateuswmachado.votesystem.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Model mapper configuration class for object conversion
 *
 * @author Mateus W. Machado
 * @since 02/02/2022
 * @version 1.0.0
 */

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
