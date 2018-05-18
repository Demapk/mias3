import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiasSharedModule } from '../../shared';
import {
    ProcedureService,
    ProcedurePopupService,
    ProcedureComponent,
    ProcedureDetailComponent,
    ProcedureDialogComponent,
    ProcedurePopupComponent,
    ProcedureDeletePopupComponent,
    ProcedureDeleteDialogComponent,
    procedureRoute,
    procedurePopupRoute,
} from './';

const ENTITY_STATES = [
    ...procedureRoute,
    ...procedurePopupRoute,
];

@NgModule({
    imports: [
        MiasSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ProcedureComponent,
        ProcedureDetailComponent,
        ProcedureDialogComponent,
        ProcedureDeleteDialogComponent,
        ProcedurePopupComponent,
        ProcedureDeletePopupComponent,
    ],
    entryComponents: [
        ProcedureComponent,
        ProcedureDialogComponent,
        ProcedurePopupComponent,
        ProcedureDeleteDialogComponent,
        ProcedureDeletePopupComponent,
    ],
    providers: [
        ProcedureService,
        ProcedurePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiasProcedureModule {}
