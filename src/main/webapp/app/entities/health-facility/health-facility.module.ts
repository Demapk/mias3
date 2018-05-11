import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiasSharedModule } from '../../shared';
import {
    HealthFacilityService,
    HealthFacilityPopupService,
    HealthFacilityComponent,
    HealthFacilityDetailComponent,
    HealthFacilityDialogComponent,
    HealthFacilityPopupComponent,
    HealthFacilityDeletePopupComponent,
    HealthFacilityDeleteDialogComponent,
    healthFacilityRoute,
    healthFacilityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...healthFacilityRoute,
    ...healthFacilityPopupRoute,
];

@NgModule({
    imports: [
        MiasSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        HealthFacilityComponent,
        HealthFacilityDetailComponent,
        HealthFacilityDialogComponent,
        HealthFacilityDeleteDialogComponent,
        HealthFacilityPopupComponent,
        HealthFacilityDeletePopupComponent,
    ],
    entryComponents: [
        HealthFacilityComponent,
        HealthFacilityDialogComponent,
        HealthFacilityPopupComponent,
        HealthFacilityDeleteDialogComponent,
        HealthFacilityDeletePopupComponent,
    ],
    providers: [
        HealthFacilityService,
        HealthFacilityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiasHealthFacilityModule {}
