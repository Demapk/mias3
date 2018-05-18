import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Facility } from './facility.model';
import { FacilityService } from './facility.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-facility',
    templateUrl: './facility.component.html'
})
export class FacilityComponent implements OnInit, OnDestroy {
facilities: Facility[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private facilityService: FacilityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.facilityService.query().subscribe(
            (res: HttpResponse<Facility[]>) => {
                this.facilities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFacilities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Facility) {
        return item.id;
    }
    registerChangeInFacilities() {
        this.eventSubscriber = this.eventManager.subscribe('facilityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
