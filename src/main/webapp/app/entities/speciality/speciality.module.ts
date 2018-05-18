import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiasSharedModule } from '../../shared';
import {
    SpecialityService,
    SpecialityPopupService,
    SpecialityComponent,
    SpecialityDetailComponent,
    SpecialityDialogComponent,
    SpecialityPopupComponent,
    SpecialityDeletePopupComponent,
    SpecialityDeleteDialogComponent,
    specialityRoute,
    specialityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...specialityRoute,
    ...specialityPopupRoute,
];

@NgModule({
    imports: [
        MiasSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SpecialityComponent,
        SpecialityDetailComponent,
        SpecialityDialogComponent,
        SpecialityDeleteDialogComponent,
        SpecialityPopupComponent,
        SpecialityDeletePopupComponent,
    ],
    entryComponents: [
        SpecialityComponent,
        SpecialityDialogComponent,
        SpecialityPopupComponent,
        SpecialityDeleteDialogComponent,
        SpecialityDeletePopupComponent,
    ],
    providers: [
        SpecialityService,
        SpecialityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiasSpecialityModule {}
