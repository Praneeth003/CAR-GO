import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CartComponent } from './cart.component';
import { CartPreviewComponent } from './cart-preview/cart-preview.component';
import { AddonsComponent } from './addons/addons.component';

@NgModule({
  declarations: [CartComponent, CartPreviewComponent, AddonsComponent],
  imports: [
    CommonModule
  ]
})
export class CartModule { }
