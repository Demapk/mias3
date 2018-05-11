import { BaseEntity } from './../../shared';

export class Speciality implements BaseEntity {
    constructor(
        public id?: number,
        public speciality?: string,
        public doctors?: BaseEntity[],
    ) {
    }
}
