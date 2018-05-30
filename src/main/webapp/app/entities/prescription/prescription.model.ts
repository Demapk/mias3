import { BaseEntity } from './../../shared';

export class Prescription implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public symptoms?: string,
        public examination?: string,
        public appointmentId?: number,
        public doctorId?: number,
        public patientId?: number,
    ) {
    }
}
