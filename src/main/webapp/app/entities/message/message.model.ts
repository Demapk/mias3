import { BaseEntity } from './../../shared';

export class Message implements BaseEntity {
    constructor(
        public id?: number,
        public message?: string,
        public sentTime?: any,
        public userLogin?: string,
        public userId?: number,
    ) {
    }
}
