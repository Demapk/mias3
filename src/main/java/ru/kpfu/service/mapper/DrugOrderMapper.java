package ru.kpfu.service.mapper;

import ru.kpfu.domain.*;
import ru.kpfu.service.dto.DrugOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DrugOrder and its DTO DrugOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {DoctorMapper.class, DrugMapper.class, PrescriptionMapper.class, PatientMapper.class})
public interface DrugOrderMapper extends EntityMapper<DrugOrderDTO, DrugOrder> {

    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "drug.id", target = "drugId")
    @Mapping(source = "prescription.id", target = "prescriptionId")
    @Mapping(source = "patient.id", target = "patientId")
    DrugOrderDTO toDto(DrugOrder drugOrder);

    @Mapping(source = "doctorId", target = "doctor")
    @Mapping(source = "drugId", target = "drug")
    @Mapping(source = "prescriptionId", target = "prescription")
    @Mapping(source = "patientId", target = "patient")
    DrugOrder toEntity(DrugOrderDTO drugOrderDTO);

    default DrugOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        DrugOrder drugOrder = new DrugOrder();
        drugOrder.setId(id);
        return drugOrder;
    }
}
