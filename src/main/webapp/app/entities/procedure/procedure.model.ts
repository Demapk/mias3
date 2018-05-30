import { BaseEntity } from './../../shared';

export class Procedure implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public code?: string,
        public parentCode?: string,
        public nodeCount?: number,
        public additionalInfo?: string,
    ) {
    }
}
