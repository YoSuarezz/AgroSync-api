package com.sedikev.application.mapper;

import com.sedikev.application.dto.AnimalDTO;
import com.sedikev.domain.entity.Animal;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AnimalMapper {

    AnimalDTO toDTO(Animal animal);
    Animal toEntity(AnimalDTO animalDTO);

    List<AnimalDTO> toDTOList(List<Animal> animals);
    List<Animal> toEntityList(List<AnimalDTO> animalDTOs);
}
