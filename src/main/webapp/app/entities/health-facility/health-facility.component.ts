import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { HealthFacility } from './health-facility.model';
import { HealthFacilityService } from './health-facility.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-health-facility',
    templateUrl: './health-facility.component.html'
})
export class HealthFacilityComponent implements OnInit, OnDestroy {
healthFacilities: HealthFacility[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private healthFacilityService: HealthFacilityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.healthFacilityService.query().subscribe(
            (res: HttpResponse<HealthFacility[]>) => {
                this.healthFacilities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInHealthFacilities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: HealthFacility) {
        return item.id;
    }
    registerChangeInHealthFacilities() {
        this.eventSubscriber = this.eventManager.subscribe('healthFacilityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
