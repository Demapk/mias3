import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiasSharedModule } from '../../shared';
import {
    DiseasService,
    DiseasPopupService,
    DiseasComponent,
    DiseasDetailComponent,
    DiseasDialogComponent,
    DiseasPopupComponent,
    DiseasDeletePopupComponent,
    DiseasDeleteDialogComponent,
    diseasRoute,
    diseasPopupRoute,
} from './';

const ENTITY_STATES = [
    ...diseasRoute,
    ...diseasPopupRoute,
];

@NgModule({
    imports: [
        MiasSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DiseasComponent,
        DiseasDetailComponent,
        DiseasDialogComponent,
        DiseasDeleteDialogComponent,
        DiseasPopupComponent,
        DiseasDeletePopupComponent,
    ],
    entryComponents: [
        DiseasComponent,
        DiseasDialogComponent,
        DiseasPopupComponent,
        DiseasDeleteDialogComponent,
        DiseasDeletePopupComponent,
    ],
    providers: [
        DiseasService,
        DiseasPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiasDiseasModule {}
