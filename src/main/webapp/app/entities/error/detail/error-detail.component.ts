import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IError } from '../error.model';

@Component({
  selector: 'jhi-error-detail',
  templateUrl: './error-detail.component.html',
})
export class ErrorDetailComponent implements OnInit {
  error: IError | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ error }) => {
      this.error = error;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
