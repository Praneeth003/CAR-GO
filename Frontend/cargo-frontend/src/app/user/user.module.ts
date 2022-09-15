import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomePageComponent } from './home-page/home-page.component';
import { JourneyDetailsComponent } from './journey-details/journey-details.component';

@NgModule({
  declarations: [HomePageComponent, JourneyDetailsComponent],
  imports: [
    CommonModule
  ]
})
export class UserModule { }
