import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { HealthFacility } from './health-facility.model';
import { HealthFacilityService } from './health-facility.service';

@Injectable()
export class HealthFacilityPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private healthFacilityService: HealthFacilityService

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
                this.healthFacilityService.find(id)
                    .subscribe((healthFacilityResponse: HttpResponse<HealthFacility>) => {
                        const healthFacility: HealthFacility = healthFacilityResponse.body;
                        this.ngbModalRef = this.healthFacilityModalRef(component, healthFacility);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.healthFacilityModalRef(component, new HealthFacility());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    healthFacilityModalRef(component: Component, healthFacility: HealthFacility): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.healthFacility = healthFacility;
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
