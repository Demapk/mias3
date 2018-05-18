import { BaseEntity } from './../../shared';

export class DrugOrder implements BaseEntity {
    constructor(
        public id?: number,
        public dose?: number,
        public dailyDose?: number,
        public units?: string,
        public frequencey?: string,
        public quantity?: number,
        public doctorId?: number,
        public drugId?: number,
        public prescriptionId?: number,
        public patientId?: number,
    ) {
    }
}
