import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiasSharedModule } from '../../shared';
import {
    FacilityService,
    FacilityPopupService,
    FacilityComponent,
    FacilityDetailComponent,
    FacilityDialogComponent,
    FacilityPopupComponent,
    FacilityDeletePopupComponent,
    FacilityDeleteDialogComponent,
    facilityRoute,
    facilityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...facilityRoute,
    ...facilityPopupRoute,
];

@NgModule({
    imports: [
        MiasSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FacilityComponent,
        FacilityDetailComponent,
        FacilityDialogComponent,
        FacilityDeleteDialogComponent,
        FacilityPopupComponent,
        FacilityDeletePopupComponent,
    ],
    entryComponents: [
        FacilityComponent,
        FacilityDialogComponent,
        FacilityPopupComponent,
        FacilityDeleteDialogComponent,
        FacilityDeletePopupComponent,
    ],
    providers: [
        FacilityService,
        FacilityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiasFacilityModule {}
