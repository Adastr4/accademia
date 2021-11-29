import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Search } from 'app/core/request/request.model';
import { IError, getErrorIdentifier } from '../error.model';

export type EntityResponseType = HttpResponse<IError>;
export type EntityArrayResponseType = HttpResponse<IError[]>;

@Injectable({ providedIn: 'root' })
export class ErrorService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/errors');
  protected resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/errors');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(error: IError): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(error);
    return this.http
      .post<IError>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(error: IError): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(error);
    return this.http
      .put<IError>(`${this.resourceUrl}/${getErrorIdentifier(error) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(error: IError): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(error);
    return this.http
      .patch<IError>(`${this.resourceUrl}/${getErrorIdentifier(error) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IError>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IError[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IError[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addErrorToCollectionIfMissing(errorCollection: IError[], ...errorsToCheck: (IError | null | undefined)[]): IError[] {
    const errors: IError[] = errorsToCheck.filter(isPresent);
    if (errors.length > 0) {
      const errorCollectionIdentifiers = errorCollection.map(errorItem => getErrorIdentifier(errorItem)!);
      const errorsToAdd = errors.filter(errorItem => {
        const errorIdentifier = getErrorIdentifier(errorItem);
        if (errorIdentifier == null || errorCollectionIdentifiers.includes(errorIdentifier)) {
          return false;
        }
        errorCollectionIdentifiers.push(errorIdentifier);
        return true;
      });
      return [...errorsToAdd, ...errorCollection];
    }
    return errorCollection;
  }

  protected convertDateFromClient(error: IError): IError {
    return Object.assign({}, error, {
      data: error.data?.isValid() ? error.data.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.data = res.body.data ? dayjs(res.body.data) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((error: IError) => {
        error.data = error.data ? dayjs(error.data) : undefined;
      });
    }
    return res;
  }
}
