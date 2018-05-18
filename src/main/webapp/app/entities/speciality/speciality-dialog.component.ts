import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Speciality } from './speciality.model';
import { SpecialityPopupService } from './speciality-popup.service';
import { SpecialityService } from './speciality.service';

@Component({
    selector: 'jhi-speciality-dialog',
    templateUrl: './speciality-dialog.component.html'
})
export class SpecialityDialogComponent implements OnInit {

    speciality: Speciality;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private specialityService: SpecialityService,
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
        if (this.speciality.id !== undefined) {
            this.subscribeToSaveResponse(
                this.specialityService.update(this.speciality));
        } else {
            this.subscribeToSaveResponse(
                this.specialityService.create(this.speciality));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Speciality>>) {
        result.subscribe((res: HttpResponse<Speciality>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Speciality) {
        this.eventManager.broadcast({ name: 'specialityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-speciality-popup',
    template: ''
})
export class SpecialityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private specialityPopupService: SpecialityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.specialityPopupService
                    .open(SpecialityDialogComponent as Component, params['id']);
            } else {
                this.specialityPopupService
                    .open(SpecialityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
