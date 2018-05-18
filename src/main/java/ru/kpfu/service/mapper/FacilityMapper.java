package ru.kpfu.service.mapper;

import ru.kpfu.domain.*;
import ru.kpfu.service.dto.FacilityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Facility and its DTO FacilityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FacilityMapper extends EntityMapper<FacilityDTO, Facility> {


    @Mapping(target = "doctors", ignore = true)
    Facility toEntity(FacilityDTO facilityDTO);

    default Facility fromId(Long id) {
        if (id == null) {
            return null;
        }
        Facility facility = new Facility();
        facility.setId(id);
        return facility;
    }
}
