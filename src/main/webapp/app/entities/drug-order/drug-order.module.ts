import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiasSharedModule } from '../../shared';
import {
    DrugOrderService,
    DrugOrderPopupService,
    DrugOrderComponent,
    DrugOrderDetailComponent,
    DrugOrderDialogComponent,
    DrugOrderPopupComponent,
    DrugOrderDeletePopupComponent,
    DrugOrderDeleteDialogComponent,
    drugOrderRoute,
    drugOrderPopupRoute,
} from './';

const ENTITY_STATES = [
    ...drugOrderRoute,
    ...drugOrderPopupRoute,
];

@NgModule({
    imports: [
        MiasSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DrugOrderComponent,
        DrugOrderDetailComponent,
        DrugOrderDialogComponent,
        DrugOrderDeleteDialogComponent,
        DrugOrderPopupComponent,
        DrugOrderDeletePopupComponent,
    ],
    entryComponents: [
        DrugOrderComponent,
        DrugOrderDialogComponent,
        DrugOrderPopupComponent,
        DrugOrderDeleteDialogComponent,
        DrugOrderDeletePopupComponent,
    ],
    providers: [
        DrugOrderService,
        DrugOrderPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiasDrugOrderModule {}
