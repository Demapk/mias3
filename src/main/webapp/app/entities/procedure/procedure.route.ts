import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ProcedureComponent } from './procedure.component';
import { ProcedureDetailComponent } from './procedure-detail.component';
import { ProcedurePopupComponent } from './procedure-dialog.component';
import { ProcedureDeletePopupComponent } from './procedure-delete-dialog.component';

export const procedureRoute: Routes = [
    {
        path: 'procedure',
        component: ProcedureComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Procedures'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'procedure/:id',
        component: ProcedureDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Procedures'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const procedurePopupRoute: Routes = [
    {
        path: 'procedure-new',
        component: ProcedurePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Procedures'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'procedure/:id/edit',
        component: ProcedurePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Procedures'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'procedure/:id/delete',
        component: ProcedureDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Procedures'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
