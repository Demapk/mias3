import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DrugOrder } from './drug-order.model';
import { DrugOrderPopupService } from './drug-order-popup.service';
import { DrugOrderService } from './drug-order.service';
import { Doctor, DoctorService } from '../doctor';
import { Drug, DrugService } from '../drug';
import { Prescription, PrescriptionService } from '../prescription';
import { Patient, PatientService } from '../patient';

@Component({
    selector: 'jhi-drug-order-dialog',
    templateUrl: './drug-order-dialog.component.html'
})
export class DrugOrderDialogComponent implements OnInit {

    drugOrder: DrugOrder;
    isSaving: boolean;

    doctors: Doctor[];

    drugs: Drug[];

    prescriptions: Prescription[];

    patients: Patient[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private drugOrderService: DrugOrderService,
        private doctorService: DoctorService,
        private drugService: DrugService,
        private prescriptionService: PrescriptionService,
        private patientService: PatientService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.doctorService.query()
            .subscribe((res: HttpResponse<Doctor[]>) => { this.doctors = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.drugService.query()
            .subscribe((res: HttpResponse<Drug[]>) => { this.drugs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.prescriptionService.query()
            .subscribe((res: HttpResponse<Prescription[]>) => { this.prescriptions = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.patientService.query()
            .subscribe((res: HttpResponse<Patient[]>) => { this.patients = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.drugOrder.id !== undefined) {
            this.subscribeToSaveResponse(
                this.drugOrderService.update(this.drugOrder));
        } else {
            this.subscribeToSaveResponse(
                this.drugOrderService.create(this.drugOrder));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DrugOrder>>) {
        result.subscribe((res: HttpResponse<DrugOrder>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DrugOrder) {
        this.eventManager.broadcast({ name: 'drugOrderListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDoctorById(index: number, item: Doctor) {
        return item.id;
    }

    trackDrugById(index: number, item: Drug) {
        return item.id;
    }

    trackPrescriptionById(index: number, item: Prescription) {
        return item.id;
    }

    trackPatientById(index: number, item: Patient) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-drug-order-popup',
    template: ''
})
export class DrugOrderPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private drugOrderPopupService: DrugOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.drugOrderPopupService
                    .open(DrugOrderDialogComponent as Component, params['id']);
            } else {
                this.drugOrderPopupService
                    .open(DrugOrderDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
