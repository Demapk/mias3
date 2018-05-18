import { BaseEntity } from './../../shared';

export const enum Gender {
    'MALE',
    'FEMALE',
    'OTHER'
}

export class Patient implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public patronymic?: string,
        public birthDay?: any,
        public rp?: number,
        public phone?: string,
        public gender?: Gender,
        public userId?: number,
    ) {
    }
}
