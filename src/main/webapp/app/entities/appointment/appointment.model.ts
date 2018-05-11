import { BaseEntity } from './../../shared';

export const enum AppointmentStatus {
    'FREE',
    'BUSY'
}

export class Appointment implements BaseEntity {
    constructor(
        public id?: number,
        public startTime?: any,
        public endTime?: any,
        public status?: AppointmentStatus,
        public doctorId?: number,
        public patientId?: number,
    ) {
    }
}
