import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Diseas } from './diseas.model';
import { DiseasService } from './diseas.service';

@Component({
    selector: 'jhi-diseas-detail',
    templateUrl: './diseas-detail.component.html'
})
export class DiseasDetailComponent implements OnInit, OnDestroy {

    diseas: Diseas;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private diseasService: DiseasService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDiseas();
    }

    load(id) {
        this.diseasService.find(id)
            .subscribe((diseasResponse: HttpResponse<Diseas>) => {
                this.diseas = diseasResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDiseas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'diseasListModification',
            (response) => this.load(this.diseas.id)
        );
    }
}
