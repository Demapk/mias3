import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Treatment } from './treatment.model';
import { TreatmentService } from './treatment.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-treatment',
    templateUrl: './treatment.component.html'
})
export class TreatmentComponent implements OnInit, OnDestroy {
treatments: Treatment[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private treatmentService: TreatmentService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.treatmentService.query().subscribe(
            (res: HttpResponse<Treatment[]>) => {
                this.treatments = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTreatments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Treatment) {
        return item.id;
    }
    registerChangeInTreatments() {
        this.eventSubscriber = this.eventManager.subscribe('treatmentListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
