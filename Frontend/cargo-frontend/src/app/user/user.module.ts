import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomePageComponent } from './home-page/home-page.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { JourneyDetailsComponent } from './journey-details/journey-details.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
// import {MatDatepickerModule, MatInputModule,MatNativeDateModule} from '@angular/material';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { SharedModule } from '../shared/shared.module';
import { UtilsModule } from '../shared/utils/utils/utils.module';
import { ConstantsModule } from '../shared/constants.module';
import { CarPageComponent } from './car-page/car-page.component';
import { GuestUserCancelComponent } from './guest-user-cancel/guest-user-cancel.component';

@NgModule({
  declarations: [HomePageComponent, JourneyDetailsComponent, CarPageComponent, GuestUserCancelComponent],
  imports: [
    CommonModule,
    BrowserAnimationsModule,
    NgbModule,
    FormsModule,
    HttpClientModule,
    SharedModule,
    UtilsModule,
    ConstantsModule

  ]
})
export class UserModule { }
