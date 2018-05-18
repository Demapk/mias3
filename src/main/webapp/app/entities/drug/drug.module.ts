import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiasSharedModule } from '../../shared';
import {
    DrugService,
    DrugPopupService,
    DrugComponent,
    DrugDetailComponent,
    DrugDialogComponent,
    DrugPopupComponent,
    DrugDeletePopupComponent,
    DrugDeleteDialogComponent,
    drugRoute,
    drugPopupRoute,
} from './';

const ENTITY_STATES = [
    ...drugRoute,
    ...drugPopupRoute,
];

@NgModule({
    imports: [
        MiasSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DrugComponent,
        DrugDetailComponent,
        DrugDialogComponent,
        DrugDeleteDialogComponent,
        DrugPopupComponent,
        DrugDeletePopupComponent,
    ],
    entryComponents: [
        DrugComponent,
        DrugDialogComponent,
        DrugPopupComponent,
        DrugDeleteDialogComponent,
        DrugDeletePopupComponent,
    ],
    providers: [
        DrugService,
        DrugPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiasDrugModule {}
