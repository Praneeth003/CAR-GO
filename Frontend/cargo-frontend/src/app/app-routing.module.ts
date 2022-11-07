import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CreateVariantComponent } from './admin/create-variant/create-variant.component';
import { DeleteVariantComponent } from './admin/delete-variant/delete-variant.component';
import { FinishBookingComponent } from './admin/finish-booking/finish-booking.component';
import { LoginComponent } from './shared/login/login.component';
import { SignupComponent } from './shared/signup/signup.component';
import { BookingHistoryComponent } from './user/booking/booking-history/booking-history.component';
import { BookingPreviewComponent } from './user/booking/booking-preview/booking-preview.component';
import { BookingSummaryComponent } from './user/booking/booking-summary/booking-summary.component';
import { BookingUpgradeComponent } from './user/booking/booking-upgrade/booking-upgrade.component';
import { PaymentComponent } from './user/booking/payment/payment.component';
import { PaymentComponent1 } from './user/booking/payment/payment.component-group';
import { CarPageComponent } from './user/car-page/car-page.component';
import { CartComponent } from './user/cart/cart.component';
import { GuestUserCancelComponent } from './user/guest-user-cancel/guest-user-cancel.component';
import { HomePageComponent } from './user/home-page/home-page.component';
import { JourneyDetailsComponent } from './user/journey-details/journey-details.component';


const routes: Routes = [
  { path: '',   redirectTo: 'login', pathMatch: 'full' },
  { path: 'user',   redirectTo: 'login', pathMatch: 'full' },
  { path: 'admin',   redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'user/journey-details', component: JourneyDetailsComponent },
  { path: 'user/home', component: HomePageComponent },
  { path: 'user/car/:carId/:actionType', component: CarPageComponent },
  { path: 'user/cart', component: CartComponent },
  { path: 'user/payment', component: PaymentComponent1 },
  { path: 'user/bookingHistory', component: BookingHistoryComponent },
  { path: 'user/upgradeBooking', component: BookingUpgradeComponent },
  { path: 'user/booking-preview', component: BookingPreviewComponent },
  { path: 'user/guest-user-cancel', component: GuestUserCancelComponent },
  { path: 'admin/create-variant', component: CreateVariantComponent },
  { path: 'admin/delete-variant', component: DeleteVariantComponent },
  { path: 'admin/finish-booking', component: FinishBookingComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
