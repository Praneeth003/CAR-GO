import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CartComponent } from './cart.component';
import { CartPreviewComponent } from './cart-preview/cart-preview.component';
import { AddonsComponent } from './addons/addons.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ConstantsModule } from 'src/app/shared/constants.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { UtilsModule } from 'src/app/shared/utils/utils/utils.module';

@NgModule({
  declarations: [CartComponent, CartPreviewComponent, AddonsComponent],
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
export class CartModule { }
