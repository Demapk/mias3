import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Prescription } from './prescription.model';
import { PrescriptionPopupService } from './prescription-popup.service';
import { PrescriptionService } from './prescription.service';
import { Appointment, AppointmentService } from '../appointment';
import { Doctor, DoctorService } from '../doctor';
import { Patient, PatientService } from '../patient';

@Component({
    selector: 'jhi-prescription-dialog',
    templateUrl: './prescription-dialog.component.html'
})
export class PrescriptionDialogComponent implements OnInit {

    prescription: Prescription;
    isSaving: boolean;

    appointments: Appointment[];

    doctors: Doctor[];

    patients: Patient[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private prescriptionService: PrescriptionService,
        private appointmentService: AppointmentService,
        private doctorService: DoctorService,
        private patientService: PatientService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.appointmentService
            .query({filter: 'prescription-is-null'})
            .subscribe((res: HttpResponse<Appointment[]>) => {
                if (!this.prescription.appointmentId) {
                    this.appointments = res.body;
                } else {
                    this.appointmentService
                        .find(this.prescription.appointmentId)
                        .subscribe((subRes: HttpResponse<Appointment>) => {
                            this.appointments = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.doctorService.query()
            .subscribe((res: HttpResponse<Doctor[]>) => { this.doctors = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.patientService.query()
            .subscribe((res: HttpResponse<Patient[]>) => { this.patients = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.prescription.id !== undefined) {
            this.subscribeToSaveResponse(
                this.prescriptionService.update(this.prescription));
        } else {
            this.subscribeToSaveResponse(
                this.prescriptionService.create(this.prescription));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Prescription>>) {
        result.subscribe((res: HttpResponse<Prescription>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Prescription) {
        this.eventManager.broadcast({ name: 'prescriptionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAppointmentById(index: number, item: Appointment) {
        return item.id;
    }

    trackDoctorById(index: number, item: Doctor) {
        return item.id;
    }

    trackPatientById(index: number, item: Patient) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-prescription-popup',
    template: ''
})
export class PrescriptionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private prescriptionPopupService: PrescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.prescriptionPopupService
                    .open(PrescriptionDialogComponent as Component, params['id']);
            } else {
                this.prescriptionPopupService
                    .open(PrescriptionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
