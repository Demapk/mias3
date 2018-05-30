import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DrugOrder } from './drug-order.model';
import { DrugOrderService } from './drug-order.service';

@Component({
    selector: 'jhi-drug-order-detail',
    templateUrl: './drug-order-detail.component.html'
})
export class DrugOrderDetailComponent implements OnInit, OnDestroy {

    drugOrder: DrugOrder;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private drugOrderService: DrugOrderService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDrugOrders();
    }

    load(id) {
        this.drugOrderService.find(id)
            .subscribe((drugOrderResponse: HttpResponse<DrugOrder>) => {
                this.drugOrder = drugOrderResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDrugOrders() {
        this.eventSubscriber = this.eventManager.subscribe(
            'drugOrderListModification',
            (response) => this.load(this.drugOrder.id)
        );
    }
}
