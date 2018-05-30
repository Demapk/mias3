import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiasSharedModule } from '../../shared';
import {
    ProcedureOrderService,
    ProcedureOrderPopupService,
    ProcedureOrderComponent,
    ProcedureOrderDetailComponent,
    ProcedureOrderDialogComponent,
    ProcedureOrderPopupComponent,
    ProcedureOrderDeletePopupComponent,
    ProcedureOrderDeleteDialogComponent,
    procedureOrderRoute,
    procedureOrderPopupRoute,
} from './';

const ENTITY_STATES = [
    ...procedureOrderRoute,
    ...procedureOrderPopupRoute,
];

@NgModule({
    imports: [
        MiasSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ProcedureOrderComponent,
        ProcedureOrderDetailComponent,
        ProcedureOrderDialogComponent,
        ProcedureOrderDeleteDialogComponent,
        ProcedureOrderPopupComponent,
        ProcedureOrderDeletePopupComponent,
    ],
    entryComponents: [
        ProcedureOrderComponent,
        ProcedureOrderDialogComponent,
        ProcedureOrderPopupComponent,
        ProcedureOrderDeleteDialogComponent,
        ProcedureOrderDeletePopupComponent,
    ],
    providers: [
        ProcedureOrderService,
        ProcedureOrderPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiasProcedureOrderModule {}
