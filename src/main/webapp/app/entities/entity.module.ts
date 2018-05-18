import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MiasDoctorModule } from './doctor/doctor.module';
import { MiasSpecialityModule } from './speciality/speciality.module';
import { MiasFacilityModule } from './facility/facility.module';
import { MiasPatientModule } from './patient/patient.module';
import { MiasDiseasModule } from './diseas/diseas.module';
import { MiasTreatmentModule } from './treatment/treatment.module';
import { MiasProcedureModule } from './procedure/procedure.module';
import { MiasDiagnosisModule } from './diagnosis/diagnosis.module';
import { MiasPrescriptionModule } from './prescription/prescription.module';
import { MiasProcedureOrderModule } from './procedure-order/procedure-order.module';
import { MiasDrugModule } from './drug/drug.module';
import { MiasDrugOrderModule } from './drug-order/drug-order.module';
import { MiasAppointmentModule } from './appointment/appointment.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        MiasDoctorModule,
        MiasSpecialityModule,
        MiasFacilityModule,
        MiasPatientModule,
        MiasDiseasModule,
        MiasTreatmentModule,
        MiasProcedureModule,
        MiasDiagnosisModule,
        MiasPrescriptionModule,
        MiasProcedureOrderModule,
        MiasDrugModule,
        MiasDrugOrderModule,
        MiasAppointmentModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiasEntityModule {}
