import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Diseas } from './diseas.model';
import { DiseasService } from './diseas.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-diseas',
    templateUrl: './diseas.component.html'
})
export class DiseasComponent implements OnInit, OnDestroy {
diseas: Diseas[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private diseasService: DiseasService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.diseasService.query().subscribe(
            (res: HttpResponse<Diseas[]>) => {
                this.diseas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDiseas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Diseas) {
        return item.id;
    }
    registerChangeInDiseas() {
        this.eventSubscriber = this.eventManager.subscribe('diseasListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
