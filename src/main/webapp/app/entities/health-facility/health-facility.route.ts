import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { HealthFacilityComponent } from './health-facility.component';
import { HealthFacilityDetailComponent } from './health-facility-detail.component';
import { HealthFacilityPopupComponent } from './health-facility-dialog.component';
import { HealthFacilityDeletePopupComponent } from './health-facility-delete-dialog.component';

export const healthFacilityRoute: Routes = [
    {
        path: 'health-facility',
        component: HealthFacilityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'miasApp.healthFacility.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'health-facility/:id',
        component: HealthFacilityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'miasApp.healthFacility.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const healthFacilityPopupRoute: Routes = [
    {
        path: 'health-facility-new',
        component: HealthFacilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'miasApp.healthFacility.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'health-facility/:id/edit',
        component: HealthFacilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'miasApp.healthFacility.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'health-facility/:id/delete',
        component: HealthFacilityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'miasApp.healthFacility.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
