import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ProcedureOrderComponent } from './procedure-order.component';
import { ProcedureOrderDetailComponent } from './procedure-order-detail.component';
import { ProcedureOrderPopupComponent } from './procedure-order-dialog.component';
import { ProcedureOrderDeletePopupComponent } from './procedure-order-delete-dialog.component';

export const procedureOrderRoute: Routes = [
    {
        path: 'procedure-order',
        component: ProcedureOrderComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProcedureOrders'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'procedure-order/:id',
        component: ProcedureOrderDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProcedureOrders'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const procedureOrderPopupRoute: Routes = [
    {
        path: 'procedure-order-new',
        component: ProcedureOrderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProcedureOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'procedure-order/:id/edit',
        component: ProcedureOrderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProcedureOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'procedure-order/:id/delete',
        component: ProcedureOrderDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProcedureOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
