import { BaseEntity } from './../../shared';

export class Doctor implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public patronymic?: string,
        public info?: string,
        public userId?: number,
        public specialityId?: number,
        public facilityId?: number,
    ) {
    }
}
