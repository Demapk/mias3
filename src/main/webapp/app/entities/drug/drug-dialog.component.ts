import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Drug } from './drug.model';
import { DrugPopupService } from './drug-popup.service';
import { DrugService } from './drug.service';

@Component({
    selector: 'jhi-drug-dialog',
    templateUrl: './drug-dialog.component.html'
})
export class DrugDialogComponent implements OnInit {

    drug: Drug;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private drugService: DrugService,
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
        if (this.drug.id !== undefined) {
            this.subscribeToSaveResponse(
                this.drugService.update(this.drug));
        } else {
            this.subscribeToSaveResponse(
                this.drugService.create(this.drug));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Drug>>) {
        result.subscribe((res: HttpResponse<Drug>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Drug) {
        this.eventManager.broadcast({ name: 'drugListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-drug-popup',
    template: ''
})
export class DrugPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private drugPopupService: DrugPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.drugPopupService
                    .open(DrugDialogComponent as Component, params['id']);
            } else {
                this.drugPopupService
                    .open(DrugDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
