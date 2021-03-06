import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DrugOrder } from './drug-order.model';
import { DrugOrderPopupService } from './drug-order-popup.service';
import { DrugOrderService } from './drug-order.service';

@Component({
    selector: 'jhi-drug-order-delete-dialog',
    templateUrl: './drug-order-delete-dialog.component.html'
})
export class DrugOrderDeleteDialogComponent {

    drugOrder: DrugOrder;

    constructor(
        private drugOrderService: DrugOrderService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.drugOrderService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'drugOrderListModification',
                content: 'Deleted an drugOrder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-drug-order-delete-popup',
    template: ''
})
export class DrugOrderDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private drugOrderPopupService: DrugOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.drugOrderPopupService
                .open(DrugOrderDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
