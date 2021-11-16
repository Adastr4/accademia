import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IError } from '../error.model';
import { ErrorService } from '../service/error.service';

@Component({
  templateUrl: './error-delete-dialog.component.html',
})
export class ErrorDeleteDialogComponent {
  error?: IError;

  constructor(protected errorService: ErrorService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.errorService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
