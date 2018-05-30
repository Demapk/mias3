import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Prescription } from './prescription.model';
import { PrescriptionService } from './prescription.service';

@Injectable()
export class PrescriptionPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private prescriptionService: PrescriptionService

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
                this.prescriptionService.find(id)
                    .subscribe((prescriptionResponse: HttpResponse<Prescription>) => {
                        const prescription: Prescription = prescriptionResponse.body;
                        prescription.date = this.datePipe
                            .transform(prescription.date, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.prescriptionModalRef(component, prescription);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.prescriptionModalRef(component, new Prescription());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    prescriptionModalRef(component: Component, prescription: Prescription): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.prescription = prescription;
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
