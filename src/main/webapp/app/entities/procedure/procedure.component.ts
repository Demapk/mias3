import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Procedure } from './procedure.model';
import { ProcedureService } from './procedure.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-procedure',
    templateUrl: './procedure.component.html'
})
export class ProcedureComponent implements OnInit, OnDestroy {
procedures: Procedure[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private procedureService: ProcedureService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.procedureService.query().subscribe(
            (res: HttpResponse<Procedure[]>) => {
                this.procedures = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInProcedures();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Procedure) {
        return item.id;
    }
    registerChangeInProcedures() {
        this.eventSubscriber = this.eventManager.subscribe('procedureListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
