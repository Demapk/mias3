import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ProcedureOrder } from './procedure-order.model';
import { ProcedureOrderPopupService } from './procedure-order-popup.service';
import { ProcedureOrderService } from './procedure-order.service';

@Component({
    selector: 'jhi-procedure-order-delete-dialog',
    templateUrl: './procedure-order-delete-dialog.component.html'
})
export class ProcedureOrderDeleteDialogComponent {

    procedureOrder: ProcedureOrder;

    constructor(
        private procedureOrderService: ProcedureOrderService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.procedureOrderService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'procedureOrderListModification',
                content: 'Deleted an procedureOrder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-procedure-order-delete-popup',
    template: ''
})
export class ProcedureOrderDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private procedureOrderPopupService: ProcedureOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.procedureOrderPopupService
                .open(ProcedureOrderDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
