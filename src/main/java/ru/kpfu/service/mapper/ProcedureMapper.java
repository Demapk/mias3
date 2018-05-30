package ru.kpfu.service.mapper;

import ru.kpfu.domain.*;
import ru.kpfu.service.dto.ProcedureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Procedure and its DTO ProcedureDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProcedureMapper extends EntityMapper<ProcedureDTO, Procedure> {



    default Procedure fromId(Long id) {
        if (id == null) {
            return null;
        }
        Procedure procedure = new Procedure();
        procedure.setId(id);
        return procedure;
    }
}
