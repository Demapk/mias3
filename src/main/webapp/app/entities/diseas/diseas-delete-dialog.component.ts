import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Diseas } from './diseas.model';
import { DiseasPopupService } from './diseas-popup.service';
import { DiseasService } from './diseas.service';

@Component({
    selector: 'jhi-diseas-delete-dialog',
    templateUrl: './diseas-delete-dialog.component.html'
})
export class DiseasDeleteDialogComponent {

    diseas: Diseas;

    constructor(
        private diseasService: DiseasService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.diseasService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'diseasListModification',
                content: 'Deleted an diseas'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-diseas-delete-popup',
    template: ''
})
export class DiseasDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private diseasPopupService: DiseasPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.diseasPopupService
                .open(DiseasDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
