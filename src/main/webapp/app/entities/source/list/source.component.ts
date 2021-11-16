import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISource } from '../source.model';
import { SourceService } from '../service/source.service';
import { SourceDeleteDialogComponent } from '../delete/source-delete-dialog.component';

@Component({
  selector: 'jhi-source',
  templateUrl: './source.component.html',
})
export class SourceComponent implements OnInit {
  sources?: ISource[];
  isLoading = false;
  currentSearch: string;

  constructor(protected sourceService: SourceService, protected modalService: NgbModal, protected activatedRoute: ActivatedRoute) {
    this.currentSearch = this.activatedRoute.snapshot.queryParams['search'] ?? '';
  }

  loadAll(): void {
    this.isLoading = true;
    if (this.currentSearch) {
      this.sourceService
        .search({
          query: this.currentSearch,
        })
        .subscribe(
          (res: HttpResponse<ISource[]>) => {
            this.isLoading = false;
            this.sources = res.body ?? [];
          },
          () => {
            this.isLoading = false;
          }
        );
      return;
    }

    this.sourceService.query().subscribe(
      (res: HttpResponse<ISource[]>) => {
        this.isLoading = false;
        this.sources = res.body ?? [];
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

  trackId(index: number, item: ISource): number {
    return item.id!;
  }

  delete(source: ISource): void {
    const modalRef = this.modalService.open(SourceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.source = source;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
