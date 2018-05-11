import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { HealthFacility } from './health-facility.model';
import { HealthFacilityPopupService } from './health-facility-popup.service';
import { HealthFacilityService } from './health-facility.service';

@Component({
    selector: 'jhi-health-facility-delete-dialog',
    templateUrl: './health-facility-delete-dialog.component.html'
})
export class HealthFacilityDeleteDialogComponent {

    healthFacility: HealthFacility;

    constructor(
        private healthFacilityService: HealthFacilityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.healthFacilityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'healthFacilityListModification',
                content: 'Deleted an healthFacility'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-health-facility-delete-popup',
    template: ''
})
export class HealthFacilityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private healthFacilityPopupService: HealthFacilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.healthFacilityPopupService
                .open(HealthFacilityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
