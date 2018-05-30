package ru.kpfu.service.mapper;

import ru.kpfu.domain.*;
import ru.kpfu.service.dto.PrescriptionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Prescription and its DTO PrescriptionDTO.
 */
@Mapper(componentModel = "spring", uses = {AppointmentMapper.class, DoctorMapper.class, PatientMapper.class})
public interface PrescriptionMapper extends EntityMapper<PrescriptionDTO, Prescription> {

    @Mapping(source = "appointment.id", target = "appointmentId")
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "patient.id", target = "patientId")
    PrescriptionDTO toDto(Prescription prescription);

    @Mapping(source = "appointmentId", target = "appointment")
    @Mapping(source = "doctorId", target = "doctor")
    @Mapping(source = "patientId", target = "patient")
    Prescription toEntity(PrescriptionDTO prescriptionDTO);

    default Prescription fromId(Long id) {
        if (id == null) {
            return null;
        }
        Prescription prescription = new Prescription();
        prescription.setId(id);
        return prescription;
    }
}
