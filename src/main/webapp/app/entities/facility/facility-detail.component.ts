import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Facility } from './facility.model';
import { FacilityService } from './facility.service';

@Component({
    selector: 'jhi-facility-detail',
    templateUrl: './facility-detail.component.html'
})
export class FacilityDetailComponent implements OnInit, OnDestroy {

    facility: Facility;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private facilityService: FacilityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFacilities();
    }

    load(id) {
        this.facilityService.find(id)
            .subscribe((facilityResponse: HttpResponse<Facility>) => {
                this.facility = facilityResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFacilities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'facilityListModification',
            (response) => this.load(this.facility.id)
        );
    }
}
