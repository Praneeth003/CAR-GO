import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LandingPageComponent } from './landing-page.component';
import { CardsComponent } from './cards/cards.component';
import { FilterComponent } from './filter/filter.component';

@NgModule({
  declarations: [LandingPageComponent, CardsComponent, FilterComponent],
  imports: [
    CommonModule
  ]
})
export class LandingPageModule { }
