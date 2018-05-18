import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TreatmentComponent } from './treatment.component';
import { TreatmentDetailComponent } from './treatment-detail.component';
import { TreatmentPopupComponent } from './treatment-dialog.component';
import { TreatmentDeletePopupComponent } from './treatment-delete-dialog.component';

export const treatmentRoute: Routes = [
    {
        path: 'treatment',
        component: TreatmentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Treatments'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'treatment/:id',
        component: TreatmentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Treatments'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const treatmentPopupRoute: Routes = [
    {
        path: 'treatment-new',
        component: TreatmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Treatments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'treatment/:id/edit',
        component: TreatmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Treatments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'treatment/:id/delete',
        component: TreatmentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Treatments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
