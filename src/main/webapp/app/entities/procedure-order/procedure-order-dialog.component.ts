import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ProcedureOrder } from './procedure-order.model';
import { ProcedureOrderPopupService } from './procedure-order-popup.service';
import { ProcedureOrderService } from './procedure-order.service';
import { Procedure, ProcedureService } from '../procedure';
import { Prescription, PrescriptionService } from '../prescription';
import { Doctor, DoctorService } from '../doctor';
import { Patient, PatientService } from '../patient';

@Component({
    selector: 'jhi-procedure-order-dialog',
    templateUrl: './procedure-order-dialog.component.html'
})
export class ProcedureOrderDialogComponent implements OnInit {

    procedureOrder: ProcedureOrder;
    isSaving: boolean;

    procedures: Procedure[];

    prescriptions: Prescription[];

    doctors: Doctor[];

    patients: Patient[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private procedureOrderService: ProcedureOrderService,
        private procedureService: ProcedureService,
        private prescriptionService: PrescriptionService,
        private doctorService: DoctorService,
        private patientService: PatientService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.procedureService.query()
            .subscribe((res: HttpResponse<Procedure[]>) => { this.procedures = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.prescriptionService.query()
            .subscribe((res: HttpResponse<Prescription[]>) => { this.prescriptions = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.procedureOrder.id !== undefined) {
            this.subscribeToSaveResponse(
                this.procedureOrderService.update(this.procedureOrder));
        } else {
            this.subscribeToSaveResponse(
                this.procedureOrderService.create(this.procedureOrder));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ProcedureOrder>>) {
        result.subscribe((res: HttpResponse<ProcedureOrder>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ProcedureOrder) {
        this.eventManager.broadcast({ name: 'procedureOrderListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackProcedureById(index: number, item: Procedure) {
        return item.id;
    }

    trackPrescriptionById(index: number, item: Prescription) {
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
    selector: 'jhi-procedure-order-popup',
    template: ''
})
export class ProcedureOrderPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private procedureOrderPopupService: ProcedureOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.procedureOrderPopupService
                    .open(ProcedureOrderDialogComponent as Component, params['id']);
            } else {
                this.procedureOrderPopupService
                    .open(ProcedureOrderDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
