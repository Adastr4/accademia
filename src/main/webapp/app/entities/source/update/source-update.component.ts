import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISource, Source } from '../source.model';
import { SourceService } from '../service/source.service';
import { Fonte } from 'app/entities/enumerations/fonte.model';

@Component({
  selector: 'jhi-source-update',
  templateUrl: './source-update.component.html',
})
export class SourceUpdateComponent implements OnInit {
  isSaving = false;
  fonteValues = Object.keys(Fonte);

  editForm = this.fb.group({
    id: [],
    sourceid: [],
    description: [],
    fonte: [],
    data: [],
  });

  constructor(protected sourceService: SourceService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ source }) => {
      if (source.id === undefined) {
        const today = dayjs().startOf('day');
        source.data = today;
      }

      this.updateForm(source);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const source = this.createFromForm();
    if (source.id !== undefined) {
      this.subscribeToSaveResponse(this.sourceService.update(source));
    } else {
      this.subscribeToSaveResponse(this.sourceService.create(source));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISource>>): void {
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

  protected updateForm(source: ISource): void {
    this.editForm.patchValue({
      id: source.id,
      sourceid: source.sourceid,
      description: source.description,
      fonte: source.fonte,
      data: source.data ? source.data.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ISource {
    return {
      ...new Source(),
      id: this.editForm.get(['id'])!.value,
      sourceid: this.editForm.get(['sourceid'])!.value,
      description: this.editForm.get(['description'])!.value,
      fonte: this.editForm.get(['fonte'])!.value,
      data: this.editForm.get(['data'])!.value ? dayjs(this.editForm.get(['data'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
