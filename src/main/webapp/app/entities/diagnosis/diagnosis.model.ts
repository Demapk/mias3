import { BaseEntity } from './../../shared';

export class Diagnosis implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public diseasId?: number,
        public patientId?: number,
        public doctorId?: number,
        public prescriptionId?: number,
    ) {
    }
}
