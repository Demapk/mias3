import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiasSharedModule } from '../../shared';
import {
    DiagnosisService,
    DiagnosisPopupService,
    DiagnosisComponent,
    DiagnosisDetailComponent,
    DiagnosisDialogComponent,
    DiagnosisPopupComponent,
    DiagnosisDeletePopupComponent,
    DiagnosisDeleteDialogComponent,
    diagnosisRoute,
    diagnosisPopupRoute,
} from './';

const ENTITY_STATES = [
    ...diagnosisRoute,
    ...diagnosisPopupRoute,
];

@NgModule({
    imports: [
        MiasSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DiagnosisComponent,
        DiagnosisDetailComponent,
        DiagnosisDialogComponent,
        DiagnosisDeleteDialogComponent,
        DiagnosisPopupComponent,
        DiagnosisDeletePopupComponent,
    ],
    entryComponents: [
        DiagnosisComponent,
        DiagnosisDialogComponent,
        DiagnosisPopupComponent,
        DiagnosisDeleteDialogComponent,
        DiagnosisDeletePopupComponent,
    ],
    providers: [
        DiagnosisService,
        DiagnosisPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiasDiagnosisModule {}
