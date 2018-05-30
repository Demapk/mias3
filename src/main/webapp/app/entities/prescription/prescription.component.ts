import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Prescription } from './prescription.model';
import { PrescriptionService } from './prescription.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-prescription',
    templateUrl: './prescription.component.html'
})
export class PrescriptionComponent implements OnInit, OnDestroy {
prescriptions: Prescription[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private prescriptionService: PrescriptionService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.prescriptionService.query().subscribe(
            (res: HttpResponse<Prescription[]>) => {
                this.prescriptions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPrescriptions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Prescription) {
        return item.id;
    }
    registerChangeInPrescriptions() {
        this.eventSubscriber = this.eventManager.subscribe('prescriptionListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
