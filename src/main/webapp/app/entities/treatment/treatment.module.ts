import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiasSharedModule } from '../../shared';
import {
    TreatmentService,
    TreatmentPopupService,
    TreatmentComponent,
    TreatmentDetailComponent,
    TreatmentDialogComponent,
    TreatmentPopupComponent,
    TreatmentDeletePopupComponent,
    TreatmentDeleteDialogComponent,
    treatmentRoute,
    treatmentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...treatmentRoute,
    ...treatmentPopupRoute,
];

@NgModule({
    imports: [
        MiasSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TreatmentComponent,
        TreatmentDetailComponent,
        TreatmentDialogComponent,
        TreatmentDeleteDialogComponent,
        TreatmentPopupComponent,
        TreatmentDeletePopupComponent,
    ],
    entryComponents: [
        TreatmentComponent,
        TreatmentDialogComponent,
        TreatmentPopupComponent,
        TreatmentDeleteDialogComponent,
        TreatmentDeletePopupComponent,
    ],
    providers: [
        TreatmentService,
        TreatmentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiasTreatmentModule {}
