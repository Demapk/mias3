package ru.kpfu.service.mapper;

import ru.kpfu.domain.*;
import ru.kpfu.service.dto.DiseasDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Diseas and its DTO DiseasDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DiseasMapper extends EntityMapper<DiseasDTO, Diseas> {

    @Mapping(source = "parentId.id", target = "parentIdId")
    DiseasDTO toDto(Diseas diseas);

    @Mapping(source = "parentIdId", target = "parentId")
    Diseas toEntity(DiseasDTO diseasDTO);

    default Diseas fromId(Long id) {
        if (id == null) {
            return null;
        }
        Diseas diseas = new Diseas();
        diseas.setId(id);
        return diseas;
    }
}
