import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Speciality } from './speciality.model';
import { SpecialityService } from './speciality.service';

@Component({
    selector: 'jhi-speciality-detail',
    templateUrl: './speciality-detail.component.html'
})
export class SpecialityDetailComponent implements OnInit, OnDestroy {

    speciality: Speciality;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private specialityService: SpecialityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSpecialities();
    }

    load(id) {
        this.specialityService.find(id)
            .subscribe((specialityResponse: HttpResponse<Speciality>) => {
                this.speciality = specialityResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSpecialities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'specialityListModification',
            (response) => this.load(this.speciality.id)
        );
    }
}
