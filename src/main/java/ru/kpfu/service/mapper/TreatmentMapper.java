package ru.kpfu.service.mapper;

import ru.kpfu.domain.*;
import ru.kpfu.service.dto.TreatmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Treatment and its DTO TreatmentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TreatmentMapper extends EntityMapper<TreatmentDTO, Treatment> {

    @Mapping(source = "parentId.id", target = "parentIdId")
    TreatmentDTO toDto(Treatment treatment);

    @Mapping(source = "parentIdId", target = "parentId")
    Treatment toEntity(TreatmentDTO treatmentDTO);

    default Treatment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Treatment treatment = new Treatment();
        treatment.setId(id);
        return treatment;
    }
}
