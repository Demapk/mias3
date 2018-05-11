import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiasSharedModule } from '../../shared';
import { MiasAdminModule } from '../../admin/admin.module';
import {
    DoctorService,
    DoctorPopupService,
    DoctorComponent,
    DoctorDetailComponent,
    DoctorDialogComponent,
    DoctorPopupComponent,
    DoctorDeletePopupComponent,
    DoctorDeleteDialogComponent,
    doctorRoute,
    doctorPopupRoute,
    DoctorResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...doctorRoute,
    ...doctorPopupRoute,
];

@NgModule({
    imports: [
        MiasSharedModule,
        MiasAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DoctorComponent,
        DoctorDetailComponent,
        DoctorDialogComponent,
        DoctorDeleteDialogComponent,
        DoctorPopupComponent,
        DoctorDeletePopupComponent,
    ],
    entryComponents: [
        DoctorComponent,
        DoctorDialogComponent,
        DoctorPopupComponent,
        DoctorDeleteDialogComponent,
        DoctorDeletePopupComponent,
    ],
    providers: [
        DoctorService,
        DoctorPopupService,
        DoctorResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiasDoctorModule {}
