import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DoctorComponent } from './doctor.component';
import { DoctorDetailComponent } from './doctor-detail.component';
import { DoctorPopupComponent } from './doctor-dialog.component';
import { DoctorDeletePopupComponent } from './doctor-delete-dialog.component';

@Injectable()
export class DoctorResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const doctorRoute: Routes = [
    {
        path: 'doctor',
        component: DoctorComponent,
        resolve: {
            'pagingParams': DoctorResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Doctors'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'doctor/:id',
        component: DoctorDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Doctors'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const doctorPopupRoute: Routes = [
    {
        path: 'doctor-new',
        component: DoctorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Doctors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'doctor/:id/edit',
        component: DoctorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Doctors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'doctor/:id/delete',
        component: DoctorDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Doctors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
