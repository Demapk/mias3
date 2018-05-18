import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Speciality } from './speciality.model';
import { SpecialityService } from './speciality.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-speciality',
    templateUrl: './speciality.component.html'
})
export class SpecialityComponent implements OnInit, OnDestroy {
specialities: Speciality[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private specialityService: SpecialityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.specialityService.query().subscribe(
            (res: HttpResponse<Speciality[]>) => {
                this.specialities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSpecialities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Speciality) {
        return item.id;
    }
    registerChangeInSpecialities() {
        this.eventSubscriber = this.eventManager.subscribe('specialityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
