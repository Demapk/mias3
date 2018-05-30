import { BaseEntity } from './../../shared';

export class Drug implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public code?: string,
    ) {
    }
}
