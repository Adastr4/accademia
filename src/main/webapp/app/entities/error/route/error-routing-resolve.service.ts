import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IError, Error } from '../error.model';
import { ErrorService } from '../service/error.service';

@Injectable({ providedIn: 'root' })
export class ErrorRoutingResolveService implements Resolve<IError> {
  constructor(protected service: ErrorService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IError> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((error: HttpResponse<Error>) => {
          if (error.body) {
            return of(error.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Error());
  }
}
