import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IError } from '../error.model';
import { ErrorService } from '../service/error.service';
import { ErrorDeleteDialogComponent } from '../delete/error-delete-dialog.component';

@Component({
  selector: 'jhi-error',
  templateUrl: './error.component.html',
})
export class ErrorComponent implements OnInit {
  errors?: IError[];
  isLoading = false;
  currentSearch: string;

  constructor(protected errorService: ErrorService, protected modalService: NgbModal, protected activatedRoute: ActivatedRoute) {
    this.currentSearch = this.activatedRoute.snapshot.queryParams['search'] ?? '';
  }

  loadAll(): void {
    this.isLoading = true;
    if (this.currentSearch) {
      this.errorService
        .search({
          query: this.currentSearch,
        })
        .subscribe(
          (res: HttpResponse<IError[]>) => {
            this.isLoading = false;
            this.errors = res.body ?? [];
          },
          () => {
            this.isLoading = false;
          }
        );
      return;
    }

    this.errorService.query().subscribe(
      (res: HttpResponse<IError[]>) => {
        this.isLoading = false;
        this.errors = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IError): number {
    return item.id!;
  }

  delete(error: IError): void {
    const modalRef = this.modalService.open(ErrorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.error = error;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
