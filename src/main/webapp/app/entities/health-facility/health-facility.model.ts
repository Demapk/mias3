import { BaseEntity } from './../../shared';

export class HealthFacility implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public address?: string,
        public phoneNumber?: string,
        public doctors?: BaseEntity[],
    ) {
    }
}
