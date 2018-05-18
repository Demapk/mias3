import { BaseEntity } from './../../shared';

export class Facility implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public address?: string,
        public phoneNumber?: string,
        public info?: string,
        public doctors?: BaseEntity[],
    ) {
    }
}
