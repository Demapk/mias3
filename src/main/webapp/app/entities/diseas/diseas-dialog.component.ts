import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Diseas } from './diseas.model';
import { DiseasPopupService } from './diseas-popup.service';
import { DiseasService } from './diseas.service';

@Component({
    selector: 'jhi-diseas-dialog',
    templateUrl: './diseas-dialog.component.html'
})
export class DiseasDialogComponent implements OnInit {

    diseas: Diseas;
    isSaving: boolean;

    diseasCollection: Diseas[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private diseasService: DiseasService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.diseasService.query()
            .subscribe((res: HttpResponse<Diseas[]>) => { this.diseasCollection = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.diseas.id !== undefined) {
            this.subscribeToSaveResponse(
                this.diseasService.update(this.diseas));
        } else {
            this.subscribeToSaveResponse(
                this.diseasService.create(this.diseas));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Diseas>>) {
        result.subscribe((res: HttpResponse<Diseas>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Diseas) {
        this.eventManager.broadcast({ name: 'diseasListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDiseasById(index: number, item: Diseas) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-diseas-popup',
    template: ''
})
export class DiseasPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private diseasPopupService: DiseasPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.diseasPopupService
                    .open(DiseasDialogComponent as Component, params['id']);
            } else {
                this.diseasPopupService
                    .open(DiseasDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
