import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DrugOrderComponent } from './drug-order.component';
import { DrugOrderDetailComponent } from './drug-order-detail.component';
import { DrugOrderPopupComponent } from './drug-order-dialog.component';
import { DrugOrderDeletePopupComponent } from './drug-order-delete-dialog.component';

export const drugOrderRoute: Routes = [
    {
        path: 'drug-order',
        component: DrugOrderComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DrugOrders'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'drug-order/:id',
        component: DrugOrderDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DrugOrders'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const drugOrderPopupRoute: Routes = [
    {
        path: 'drug-order-new',
        component: DrugOrderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DrugOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'drug-order/:id/edit',
        component: DrugOrderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DrugOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'drug-order/:id/delete',
        component: DrugOrderDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DrugOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
