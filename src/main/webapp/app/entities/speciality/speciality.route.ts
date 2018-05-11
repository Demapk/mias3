import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SpecialityComponent } from './speciality.component';
import { SpecialityDetailComponent } from './speciality-detail.component';
import { SpecialityPopupComponent } from './speciality-dialog.component';
import { SpecialityDeletePopupComponent } from './speciality-delete-dialog.component';

export const specialityRoute: Routes = [
    {
        path: 'speciality',
        component: SpecialityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'miasApp.speciality.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'speciality/:id',
        component: SpecialityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'miasApp.speciality.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const specialityPopupRoute: Routes = [
    {
        path: 'speciality-new',
        component: SpecialityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'miasApp.speciality.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'speciality/:id/edit',
        component: SpecialityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'miasApp.speciality.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'speciality/:id/delete',
        component: SpecialityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'miasApp.speciality.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
