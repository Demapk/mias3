import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Diagnosis } from './diagnosis.model';
import { DiagnosisService } from './diagnosis.service';

@Injectable()
export class DiagnosisPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private diagnosisService: DiagnosisService

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
                this.diagnosisService.find(id)
                    .subscribe((diagnosisResponse: HttpResponse<Diagnosis>) => {
                        const diagnosis: Diagnosis = diagnosisResponse.body;
                        diagnosis.date = this.datePipe
                            .transform(diagnosis.date, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.diagnosisModalRef(component, diagnosis);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.diagnosisModalRef(component, new Diagnosis());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    diagnosisModalRef(component: Component, diagnosis: Diagnosis): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.diagnosis = diagnosis;
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
