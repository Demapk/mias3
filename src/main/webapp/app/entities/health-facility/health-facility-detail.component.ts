import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { HealthFacility } from './health-facility.model';
import { HealthFacilityService } from './health-facility.service';

@Component({
    selector: 'jhi-health-facility-detail',
    templateUrl: './health-facility-detail.component.html'
})
export class HealthFacilityDetailComponent implements OnInit, OnDestroy {

    healthFacility: HealthFacility;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private healthFacilityService: HealthFacilityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHealthFacilities();
    }

    load(id) {
        this.healthFacilityService.find(id)
            .subscribe((healthFacilityResponse: HttpResponse<HealthFacility>) => {
                this.healthFacility = healthFacilityResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHealthFacilities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'healthFacilityListModification',
            (response) => this.load(this.healthFacility.id)
        );
    }
}
