import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BookingPreviewComponent } from './booking-preview/booking-preview.component';
import { PaymentComponent } from './payment/payment.component';
import { BookingSummaryComponent } from './booking-summary/booking-summary.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { PaymentComponent1 } from './payment/payment.component-group';
import { BookingHistoryComponent } from './booking-history/booking-history.component';
import { BookingUpgradeComponent } from './booking-upgrade/booking-upgrade.component';

@NgModule({
  declarations: [BookingPreviewComponent, PaymentComponent,PaymentComponent1,BookingSummaryComponent, BookingHistoryComponent, BookingUpgradeComponent],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule,
    FormsModule
  ]
})
export class BookingModule { }
