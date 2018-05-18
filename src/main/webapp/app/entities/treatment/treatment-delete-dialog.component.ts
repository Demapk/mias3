import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Treatment } from './treatment.model';
import { TreatmentPopupService } from './treatment-popup.service';
import { TreatmentService } from './treatment.service';

@Component({
    selector: 'jhi-treatment-delete-dialog',
    templateUrl: './treatment-delete-dialog.component.html'
})
export class TreatmentDeleteDialogComponent {

    treatment: Treatment;

    constructor(
        private treatmentService: TreatmentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.treatmentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'treatmentListModification',
                content: 'Deleted an treatment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-treatment-delete-popup',
    template: ''
})
export class TreatmentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private treatmentPopupService: TreatmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.treatmentPopupService
                .open(TreatmentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
