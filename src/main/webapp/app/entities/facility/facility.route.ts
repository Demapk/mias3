import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { FacilityComponent } from './facility.component';
import { FacilityDetailComponent } from './facility-detail.component';
import { FacilityPopupComponent } from './facility-dialog.component';
import { FacilityDeletePopupComponent } from './facility-delete-dialog.component';

export const facilityRoute: Routes = [
    {
        path: 'facility',
        component: FacilityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Facilities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'facility/:id',
        component: FacilityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Facilities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const facilityPopupRoute: Routes = [
    {
        path: 'facility-new',
        component: FacilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Facilities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'facility/:id/edit',
        component: FacilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Facilities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'facility/:id/delete',
        component: FacilityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Facilities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
