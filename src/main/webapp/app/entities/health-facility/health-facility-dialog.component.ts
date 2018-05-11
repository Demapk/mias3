import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { HealthFacility } from './health-facility.model';
import { HealthFacilityPopupService } from './health-facility-popup.service';
import { HealthFacilityService } from './health-facility.service';

@Component({
    selector: 'jhi-health-facility-dialog',
    templateUrl: './health-facility-dialog.component.html'
})
export class HealthFacilityDialogComponent implements OnInit {

    healthFacility: HealthFacility;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private healthFacilityService: HealthFacilityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.healthFacility.id !== undefined) {
            this.subscribeToSaveResponse(
                this.healthFacilityService.update(this.healthFacility));
        } else {
            this.subscribeToSaveResponse(
                this.healthFacilityService.create(this.healthFacility));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<HealthFacility>>) {
        result.subscribe((res: HttpResponse<HealthFacility>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HealthFacility) {
        this.eventManager.broadcast({ name: 'healthFacilityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-health-facility-popup',
    template: ''
})
export class HealthFacilityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private healthFacilityPopupService: HealthFacilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.healthFacilityPopupService
                    .open(HealthFacilityDialogComponent as Component, params['id']);
            } else {
                this.healthFacilityPopupService
                    .open(HealthFacilityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
