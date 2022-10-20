import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BookingPreviewComponent } from './booking-preview/booking-preview.component';
import { PaymentComponent } from './payment/payment.component';
import { BookingSummaryComponent } from './booking-summary/booking-summary.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [BookingPreviewComponent, PaymentComponent, BookingSummaryComponent],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule,
    FormsModule
  ]
})
export class BookingModule { }
