import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ErrorComponent } from './list/error.component';
import { ErrorDetailComponent } from './detail/error-detail.component';
import { ErrorUpdateComponent } from './update/error-update.component';
import { ErrorDeleteDialogComponent } from './delete/error-delete-dialog.component';
import { ErrorRoutingModule } from './route/error-routing.module';

@NgModule({
  imports: [SharedModule, ErrorRoutingModule],
  declarations: [ErrorComponent, ErrorDetailComponent, ErrorUpdateComponent, ErrorDeleteDialogComponent],
  entryComponents: [ErrorDeleteDialogComponent],
})
export class ErrorModule {}
