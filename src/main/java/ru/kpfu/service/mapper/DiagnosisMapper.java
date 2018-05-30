package ru.kpfu.service.mapper;

import ru.kpfu.domain.*;
import ru.kpfu.service.dto.DiagnosisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Diagnosis and its DTO DiagnosisDTO.
 */
@Mapper(componentModel = "spring", uses = {DiseasMapper.class, PatientMapper.class, DoctorMapper.class, PrescriptionMapper.class})
public interface DiagnosisMapper extends EntityMapper<DiagnosisDTO, Diagnosis> {

    @Mapping(source = "diseas.id", target = "diseasId")
    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "prescription.id", target = "prescriptionId")
    DiagnosisDTO toDto(Diagnosis diagnosis);

    @Mapping(source = "diseasId", target = "diseas")
    @Mapping(source = "patientId", target = "patient")
    @Mapping(source = "doctorId", target = "doctor")
    @Mapping(source = "prescriptionId", target = "prescription")
    Diagnosis toEntity(DiagnosisDTO diagnosisDTO);

    default Diagnosis fromId(Long id) {
        if (id == null) {
            return null;
        }
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setId(id);
        return diagnosis;
    }
}
