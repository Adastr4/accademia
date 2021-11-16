import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Search } from 'app/core/request/request.model';
import { ISource, getSourceIdentifier } from '../source.model';

export type EntityResponseType = HttpResponse<ISource>;
export type EntityArrayResponseType = HttpResponse<ISource[]>;

@Injectable({ providedIn: 'root' })
export class SourceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sources');
  protected resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/sources');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(source: ISource): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(source);
    return this.http
      .post<ISource>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(source: ISource): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(source);
    return this.http
      .put<ISource>(`${this.resourceUrl}/${getSourceIdentifier(source) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(source: ISource): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(source);
    return this.http
      .patch<ISource>(`${this.resourceUrl}/${getSourceIdentifier(source) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISource>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISource[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISource[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addSourceToCollectionIfMissing(sourceCollection: ISource[], ...sourcesToCheck: (ISource | null | undefined)[]): ISource[] {
    const sources: ISource[] = sourcesToCheck.filter(isPresent);
    if (sources.length > 0) {
      const sourceCollectionIdentifiers = sourceCollection.map(sourceItem => getSourceIdentifier(sourceItem)!);
      const sourcesToAdd = sources.filter(sourceItem => {
        const sourceIdentifier = getSourceIdentifier(sourceItem);
        if (sourceIdentifier == null || sourceCollectionIdentifiers.includes(sourceIdentifier)) {
          return false;
        }
        sourceCollectionIdentifiers.push(sourceIdentifier);
        return true;
      });
      return [...sourcesToAdd, ...sourceCollection];
    }
    return sourceCollection;
  }

  protected convertDateFromClient(source: ISource): ISource {
    return Object.assign({}, source, {
      data: source.data?.isValid() ? source.data.toJSON() : undefined,
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
      res.body.forEach((source: ISource) => {
        source.data = source.data ? dayjs(source.data) : undefined;
      });
    }
    return res;
  }
}
