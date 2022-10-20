import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './shared/login/login.component';
import { SignupComponent } from './shared/signup/signup.component';
import { BookingPreviewComponent } from './user/booking/booking-preview/booking-preview.component';
import { BookingSummaryComponent } from './user/booking/booking-summary/booking-summary.component';
import { PaymentComponent } from './user/booking/payment/payment.component';
import { CarPageComponent } from './user/car-page/car-page.component';
import { CartComponent } from './user/cart/cart.component';
import { HomePageComponent } from './user/home-page/home-page.component';
import { JourneyDetailsComponent } from './user/journey-details/journey-details.component';


const routes: Routes = [
  { path: '',   redirectTo: '/user/journey-details', pathMatch: 'full' },
  { path: 'user',   redirectTo: '/user/journey-details', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'user/journey-details', component: JourneyDetailsComponent },
  { path: 'user/home', component: HomePageComponent },
  { path: 'user/car/:carId', component: CarPageComponent },
  { path: 'user/cart', component: CartComponent },
  { path: 'user/payment', component: PaymentComponent },
  { path: 'user/booking-preview', component: BookingPreviewComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
