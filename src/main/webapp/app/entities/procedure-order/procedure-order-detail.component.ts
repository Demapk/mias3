import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ProcedureOrder } from './procedure-order.model';
import { ProcedureOrderService } from './procedure-order.service';

@Component({
    selector: 'jhi-procedure-order-detail',
    templateUrl: './procedure-order-detail.component.html'
})
export class ProcedureOrderDetailComponent implements OnInit, OnDestroy {

    procedureOrder: ProcedureOrder;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private procedureOrderService: ProcedureOrderService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProcedureOrders();
    }

    load(id) {
        this.procedureOrderService.find(id)
            .subscribe((procedureOrderResponse: HttpResponse<ProcedureOrder>) => {
                this.procedureOrder = procedureOrderResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProcedureOrders() {
        this.eventSubscriber = this.eventManager.subscribe(
            'procedureOrderListModification',
            (response) => this.load(this.procedureOrder.id)
        );
    }
}
