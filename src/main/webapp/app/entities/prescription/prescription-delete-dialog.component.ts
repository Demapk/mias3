import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Prescription } from './prescription.model';
import { PrescriptionPopupService } from './prescription-popup.service';
import { PrescriptionService } from './prescription.service';

@Component({
    selector: 'jhi-prescription-delete-dialog',
    templateUrl: './prescription-delete-dialog.component.html'
})
export class PrescriptionDeleteDialogComponent {

    prescription: Prescription;

    constructor(
        private prescriptionService: PrescriptionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.prescriptionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'prescriptionListModification',
                content: 'Deleted an prescription'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-prescription-delete-popup',
    template: ''
})
export class PrescriptionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private prescriptionPopupService: PrescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.prescriptionPopupService
                .open(PrescriptionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
