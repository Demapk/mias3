import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Speciality } from './speciality.model';
import { SpecialityPopupService } from './speciality-popup.service';
import { SpecialityService } from './speciality.service';

@Component({
    selector: 'jhi-speciality-delete-dialog',
    templateUrl: './speciality-delete-dialog.component.html'
})
export class SpecialityDeleteDialogComponent {

    speciality: Speciality;

    constructor(
        private specialityService: SpecialityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.specialityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'specialityListModification',
                content: 'Deleted an speciality'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-speciality-delete-popup',
    template: ''
})
export class SpecialityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private specialityPopupService: SpecialityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.specialityPopupService
                .open(SpecialityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
