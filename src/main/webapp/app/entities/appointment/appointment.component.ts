import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Appointment } from './appointment.model';
import { AppointmentService } from './appointment.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-appointment',
    templateUrl: './appointment.component.html'
})
export class AppointmentComponent implements OnInit, OnDestroy {
appointments: Appointment[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private appointmentService: AppointmentService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.appointmentService.query().subscribe(
            (res: HttpResponse<Appointment[]>) => {
                this.appointments = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAppointments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Appointment) {
        return item.id;
    }
    registerChangeInAppointments() {
        this.eventSubscriber = this.eventManager.subscribe('appointmentListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
