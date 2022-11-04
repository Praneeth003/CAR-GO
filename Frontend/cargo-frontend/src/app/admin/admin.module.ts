import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CreateVariantComponent } from './create-variant/create-variant.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { SharedModule } from '../shared/shared.module';
import { UtilsModule } from '../shared/utils/utils/utils.module';
import { ConstantsModule } from '../shared/constants.module';

@NgModule({
  declarations: [CreateVariantComponent],
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
export class AdminModule { }
