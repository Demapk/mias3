package ru.kpfu.service.mapper;

import ru.kpfu.domain.*;
import ru.kpfu.service.dto.HealthFacilityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity HealthFacility and its DTO HealthFacilityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HealthFacilityMapper extends EntityMapper<HealthFacilityDTO, HealthFacility> {


    @Mapping(target = "doctors", ignore = true)
    HealthFacility toEntity(HealthFacilityDTO healthFacilityDTO);

    default HealthFacility fromId(Long id) {
        if (id == null) {
            return null;
        }
        HealthFacility healthFacility = new HealthFacility();
        healthFacility.setId(id);
        return healthFacility;
    }
}
