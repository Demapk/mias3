package ru.kpfu.service.mapper;

import ru.kpfu.domain.*;
import ru.kpfu.service.dto.DoctorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Doctor and its DTO DoctorDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, SpecialityMapper.class, HealthFacilityMapper.class})
public interface DoctorMapper extends EntityMapper<DoctorDTO, Doctor> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "speciality.id", target = "specialityId")
    @Mapping(source = "healthFacility.id", target = "healthFacilityId")
    DoctorDTO toDto(Doctor doctor);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "specialityId", target = "speciality")
    @Mapping(source = "healthFacilityId", target = "healthFacility")
    Doctor toEntity(DoctorDTO doctorDTO);

    default Doctor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Doctor doctor = new Doctor();
        doctor.setId(id);
        return doctor;
    }
}
