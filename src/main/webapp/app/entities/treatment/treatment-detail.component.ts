import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Treatment } from './treatment.model';
import { TreatmentService } from './treatment.service';

@Component({
    selector: 'jhi-treatment-detail',
    templateUrl: './treatment-detail.component.html'
})
export class TreatmentDetailComponent implements OnInit, OnDestroy {

    treatment: Treatment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private treatmentService: TreatmentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTreatments();
    }

    load(id) {
        this.treatmentService.find(id)
            .subscribe((treatmentResponse: HttpResponse<Treatment>) => {
                this.treatment = treatmentResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTreatments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'treatmentListModification',
            (response) => this.load(this.treatment.id)
        );
    }
}
