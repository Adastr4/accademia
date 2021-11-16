import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IError, Error } from '../error.model';
import { ErrorService } from '../service/error.service';
import { ISource } from 'app/entities/source/source.model';
import { SourceService } from 'app/entities/source/service/source.service';

@Component({
  selector: 'jhi-error-update',
  templateUrl: './error-update.component.html',
})
export class ErrorUpdateComponent implements OnInit {
  isSaving = false;

  sourcesSharedCollection: ISource[] = [];

  editForm = this.fb.group({
    id: [],
    errorid: [],
    description: [],
    data: [],
    source: [],
  });

  constructor(
    protected errorService: ErrorService,
    protected sourceService: SourceService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ error }) => {
      if (error.id === undefined) {
        const today = dayjs().startOf('day');
        error.data = today;
      }

      this.updateForm(error);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const error = this.createFromForm();
    if (error.id !== undefined) {
      this.subscribeToSaveResponse(this.errorService.update(error));
    } else {
      this.subscribeToSaveResponse(this.errorService.create(error));
    }
  }

  trackSourceById(index: number, item: ISource): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IError>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(error: IError): void {
    this.editForm.patchValue({
      id: error.id,
      errorid: error.errorid,
      description: error.description,
      data: error.data ? error.data.format(DATE_TIME_FORMAT) : null,
      source: error.source,
    });

    this.sourcesSharedCollection = this.sourceService.addSourceToCollectionIfMissing(this.sourcesSharedCollection, error.source);
  }

  protected loadRelationshipsOptions(): void {
    this.sourceService
      .query()
      .pipe(map((res: HttpResponse<ISource[]>) => res.body ?? []))
      .pipe(map((sources: ISource[]) => this.sourceService.addSourceToCollectionIfMissing(sources, this.editForm.get('source')!.value)))
      .subscribe((sources: ISource[]) => (this.sourcesSharedCollection = sources));
  }

  protected createFromForm(): IError {
    return {
      ...new Error(),
      id: this.editForm.get(['id'])!.value,
      errorid: this.editForm.get(['errorid'])!.value,
      description: this.editForm.get(['description'])!.value,
      data: this.editForm.get(['data'])!.value ? dayjs(this.editForm.get(['data'])!.value, DATE_TIME_FORMAT) : undefined,
      source: this.editForm.get(['source'])!.value,
    };
  }
}
