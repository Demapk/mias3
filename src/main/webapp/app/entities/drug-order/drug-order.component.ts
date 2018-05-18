import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DrugOrder } from './drug-order.model';
import { DrugOrderService } from './drug-order.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-drug-order',
    templateUrl: './drug-order.component.html'
})
export class DrugOrderComponent implements OnInit, OnDestroy {
drugOrders: DrugOrder[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private drugOrderService: DrugOrderService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.drugOrderService.query().subscribe(
            (res: HttpResponse<DrugOrder[]>) => {
                this.drugOrders = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDrugOrders();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DrugOrder) {
        return item.id;
    }
    registerChangeInDrugOrders() {
        this.eventSubscriber = this.eventManager.subscribe('drugOrderListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
