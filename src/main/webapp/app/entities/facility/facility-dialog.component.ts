import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Facility } from './facility.model';
import { FacilityPopupService } from './facility-popup.service';
import { FacilityService } from './facility.service';

@Component({
    selector: 'jhi-facility-dialog',
    templateUrl: './facility-dialog.component.html'
})
export class FacilityDialogComponent implements OnInit {

    facility: Facility;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private facilityService: FacilityService,
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
        if (this.facility.id !== undefined) {
            this.subscribeToSaveResponse(
                this.facilityService.update(this.facility));
        } else {
            this.subscribeToSaveResponse(
                this.facilityService.create(this.facility));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Facility>>) {
        result.subscribe((res: HttpResponse<Facility>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Facility) {
        this.eventManager.broadcast({ name: 'facilityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-facility-popup',
    template: ''
})
export class FacilityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private facilityPopupService: FacilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.facilityPopupService
                    .open(FacilityDialogComponent as Component, params['id']);
            } else {
                this.facilityPopupService
                    .open(FacilityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
