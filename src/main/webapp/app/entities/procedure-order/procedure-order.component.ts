import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ProcedureOrder } from './procedure-order.model';
import { ProcedureOrderService } from './procedure-order.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-procedure-order',
    templateUrl: './procedure-order.component.html'
})
export class ProcedureOrderComponent implements OnInit, OnDestroy {
procedureOrders: ProcedureOrder[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private procedureOrderService: ProcedureOrderService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.procedureOrderService.query().subscribe(
            (res: HttpResponse<ProcedureOrder[]>) => {
                this.procedureOrders = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInProcedureOrders();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ProcedureOrder) {
        return item.id;
    }
    registerChangeInProcedureOrders() {
        this.eventSubscriber = this.eventManager.subscribe('procedureOrderListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
