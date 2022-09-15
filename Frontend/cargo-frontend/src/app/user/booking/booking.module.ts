import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BookingPreviewComponent } from './booking-preview/booking-preview.component';
import { PaymentComponent } from './payment/payment.component';
import { BookingSummaryComponent } from './booking-summary/booking-summary.component';

@NgModule({
  declarations: [BookingPreviewComponent, PaymentComponent, BookingSummaryComponent],
  imports: [
    CommonModule
  ]
})
export class BookingModule { }
