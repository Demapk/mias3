import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Treatment } from './treatment.model';
import { TreatmentPopupService } from './treatment-popup.service';
import { TreatmentService } from './treatment.service';

@Component({
    selector: 'jhi-treatment-dialog',
    templateUrl: './treatment-dialog.component.html'
})
export class TreatmentDialogComponent implements OnInit {

    treatment: Treatment;
    isSaving: boolean;

    treatments: Treatment[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private treatmentService: TreatmentService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.treatmentService.query()
            .subscribe((res: HttpResponse<Treatment[]>) => { this.treatments = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.treatment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.treatmentService.update(this.treatment));
        } else {
            this.subscribeToSaveResponse(
                this.treatmentService.create(this.treatment));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Treatment>>) {
        result.subscribe((res: HttpResponse<Treatment>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Treatment) {
        this.eventManager.broadcast({ name: 'treatmentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTreatmentById(index: number, item: Treatment) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-treatment-popup',
    template: ''
})
export class TreatmentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private treatmentPopupService: TreatmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.treatmentPopupService
                    .open(TreatmentDialogComponent as Component, params['id']);
            } else {
                this.treatmentPopupService
                    .open(TreatmentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
