package ru.kpfu.service.mapper;

import ru.kpfu.domain.*;
import ru.kpfu.service.dto.AppointmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Appointment and its DTO AppointmentDTO.
 */
@Mapper(componentModel = "spring", uses = {DoctorMapper.class, PatientMapper.class})
public interface AppointmentMapper extends EntityMapper<AppointmentDTO, Appointment> {

    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "patient.id", target = "patientId")
    AppointmentDTO toDto(Appointment appointment);

    @Mapping(source = "doctorId", target = "doctor")
    @Mapping(source = "patientId", target = "patient")
    Appointment toEntity(AppointmentDTO appointmentDTO);

    default Appointment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Appointment appointment = new Appointment();
        appointment.setId(id);
        return appointment;
    }
}
