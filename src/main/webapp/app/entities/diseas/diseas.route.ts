import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DiseasComponent } from './diseas.component';
import { DiseasDetailComponent } from './diseas-detail.component';
import { DiseasPopupComponent } from './diseas-dialog.component';
import { DiseasDeletePopupComponent } from './diseas-delete-dialog.component';

export const diseasRoute: Routes = [
    {
        path: 'diseas',
        component: DiseasComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Diseas'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'diseas/:id',
        component: DiseasDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Diseas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const diseasPopupRoute: Routes = [
    {
        path: 'diseas-new',
        component: DiseasPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Diseas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'diseas/:id/edit',
        component: DiseasPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Diseas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'diseas/:id/delete',
        component: DiseasDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Diseas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
