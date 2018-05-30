package ru.kpfu.service.mapper;

import ru.kpfu.domain.*;
import ru.kpfu.service.dto.DrugDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Drug and its DTO DrugDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DrugMapper extends EntityMapper<DrugDTO, Drug> {



    default Drug fromId(Long id) {
        if (id == null) {
            return null;
        }
        Drug drug = new Drug();
        drug.setId(id);
        return drug;
    }
}
