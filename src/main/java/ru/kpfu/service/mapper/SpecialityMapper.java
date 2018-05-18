package ru.kpfu.service.mapper;

import ru.kpfu.domain.*;
import ru.kpfu.service.dto.SpecialityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Speciality and its DTO SpecialityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SpecialityMapper extends EntityMapper<SpecialityDTO, Speciality> {


    @Mapping(target = "doctors", ignore = true)
    Speciality toEntity(SpecialityDTO specialityDTO);

    default Speciality fromId(Long id) {
        if (id == null) {
            return null;
        }
        Speciality speciality = new Speciality();
        speciality.setId(id);
        return speciality;
    }
}
