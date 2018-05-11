import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MiasDoctorModule } from './doctor/doctor.module';
import { MiasSpecialityModule } from './speciality/speciality.module';
import { MiasHealthFacilityModule } from './health-facility/health-facility.module';
import { MiasPatientModule } from './patient/patient.module';
import { MiasAppointmentModule } from './appointment/appointment.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        MiasDoctorModule,
        MiasSpecialityModule,
        MiasHealthFacilityModule,
        MiasPatientModule,
        MiasAppointmentModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiasEntityModule {}
