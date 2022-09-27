import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import {RouterModule} from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import {SharedService} from './shared.service'
import { DatepickerComponent } from './datepicker/datepicker.component';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { SpinnerComponent } from './spinner/spinner.component';
import { NgxUiLoaderModule } from 'ngx-ui-loader';
import { ToastrModule } from 'ngx-toastr';

@NgModule({
  declarations: [HeaderComponent, FooterComponent, LoginComponent, SignupComponent,DatepickerComponent,SpinnerComponent],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    HttpClientModule,
    ToastrModule.forRoot(),
    BsDatepickerModule.forRoot(),
    NgxUiLoaderModule.forRoot({
      fgsType: 'ball-spin',
      overlayColor: 'rgba(255,255,255,0.5)',
      fgsSize: 40,
      pbThickness: 2,
      hasProgressBar:false,
      pbColor: "#0000FF"
    })
  ],
  providers: [ SharedService  ],
  exports: [HeaderComponent, FooterComponent,DatepickerComponent,SpinnerComponent]
})
export class SharedModule { }
