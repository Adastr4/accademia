import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ErrorComponent } from '../list/error.component';
import { ErrorDetailComponent } from '../detail/error-detail.component';
import { ErrorUpdateComponent } from '../update/error-update.component';
import { ErrorRoutingResolveService } from './error-routing-resolve.service';

const errorRoute: Routes = [
  {
    path: '',
    component: ErrorComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ErrorDetailComponent,
    resolve: {
      error: ErrorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ErrorUpdateComponent,
    resolve: {
      error: ErrorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ErrorUpdateComponent,
    resolve: {
      error: ErrorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(errorRoute)],
  exports: [RouterModule],
})
export class ErrorRoutingModule {}
