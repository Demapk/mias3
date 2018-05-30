import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { ProcedureOrder } from './procedure-order.model';
import { ProcedureOrderService } from './procedure-order.service';

@Injectable()
export class ProcedureOrderPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private procedureOrderService: ProcedureOrderService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.procedureOrderService.find(id)
                    .subscribe((procedureOrderResponse: HttpResponse<ProcedureOrder>) => {
                        const procedureOrder: ProcedureOrder = procedureOrderResponse.body;
                        procedureOrder.date = this.datePipe
                            .transform(procedureOrder.date, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.procedureOrderModalRef(component, procedureOrder);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.procedureOrderModalRef(component, new ProcedureOrder());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    procedureOrderModalRef(component: Component, procedureOrder: ProcedureOrder): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.procedureOrder = procedureOrder;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
