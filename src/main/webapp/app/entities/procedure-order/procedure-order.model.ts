import { BaseEntity } from './../../shared';

export class ProcedureOrder implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public procedureId?: number,
        public prescriptionId?: number,
        public doctorId?: number,
        public patientId?: number,
    ) {
    }
}
