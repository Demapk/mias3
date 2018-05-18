package ru.kpfu.service.mapper;

import ru.kpfu.domain.*;
import ru.kpfu.service.dto.ProcedureOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProcedureOrder and its DTO ProcedureOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {ProcedureMapper.class, PrescriptionMapper.class, DoctorMapper.class, PatientMapper.class})
public interface ProcedureOrderMapper extends EntityMapper<ProcedureOrderDTO, ProcedureOrder> {

    @Mapping(source = "procedure.id", target = "procedureId")
    @Mapping(source = "prescription.id", target = "prescriptionId")
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "patient.id", target = "patientId")
    ProcedureOrderDTO toDto(ProcedureOrder procedureOrder);

    @Mapping(source = "procedureId", target = "procedure")
    @Mapping(source = "prescriptionId", target = "prescription")
    @Mapping(source = "doctorId", target = "doctor")
    @Mapping(source = "patientId", target = "patient")
    ProcedureOrder toEntity(ProcedureOrderDTO procedureOrderDTO);

    default ProcedureOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProcedureOrder procedureOrder = new ProcedureOrder();
        procedureOrder.setId(id);
        return procedureOrder;
    }
}
