import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Procedure } from './procedure.model';
import { ProcedureService } from './procedure.service';

@Component({
    selector: 'jhi-procedure-detail',
    templateUrl: './procedure-detail.component.html'
})
export class ProcedureDetailComponent implements OnInit, OnDestroy {

    procedure: Procedure;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private procedureService: ProcedureService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProcedures();
    }

    load(id) {
        this.procedureService.find(id)
            .subscribe((procedureResponse: HttpResponse<Procedure>) => {
                this.procedure = procedureResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProcedures() {
        this.eventSubscriber = this.eventManager.subscribe(
            'procedureListModification',
            (response) => this.load(this.procedure.id)
        );
    }
}
