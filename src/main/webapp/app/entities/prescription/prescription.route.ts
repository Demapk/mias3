import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PrescriptionComponent } from './prescription.component';
import { PrescriptionDetailComponent } from './prescription-detail.component';
import { PrescriptionPopupComponent } from './prescription-dialog.component';
import { PrescriptionDeletePopupComponent } from './prescription-delete-dialog.component';

export const prescriptionRoute: Routes = [
    {
        path: 'prescription',
        component: PrescriptionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prescriptions'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'prescription/:id',
        component: PrescriptionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prescriptions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const prescriptionPopupRoute: Routes = [
    {
        path: 'prescription-new',
        component: PrescriptionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prescriptions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prescription/:id/edit',
        component: PrescriptionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prescriptions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prescription/:id/delete',
        component: PrescriptionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prescriptions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
