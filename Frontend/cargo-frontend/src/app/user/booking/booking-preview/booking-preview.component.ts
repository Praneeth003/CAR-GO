import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from 'angular-web-storage';
import { ToastrService } from 'ngx-toastr';
import { ConstantsModule } from 'src/app/shared/constants.module';
import { SharedService } from 'src/app/shared/shared.service';
import { UtilsModule } from 'src/app/shared/utils/utils/utils.module';

@Component({
  selector: 'app-booking-preview',
  templateUrl: './booking-preview.component.html',
  styleUrls: ['./booking-preview.component.css']
})
export class BookingPreviewComponent implements OnInit {

  mapLoaderActive = false;
  orderId = ""
  displayMessage = ""
  constructor(private waterDataService: SharedService, private constantsModule: ConstantsModule,
    private utils: UtilsModule, private router: Router, private localStorage: LocalStorageService, private toastrService: ToastrService) { }

  ngOnInit() {
    this.localStorage.remove('cartData');
    let data = this.localStorage.get('bookingInfo');
    this.orderId = data['data'].bookingInfoId;
    if(data['data'].status){
      if(data['data'].status == 'CANCELLED'){
        this.displayMessage = 'Booking Cancelled Succesfully'
      }
      if(data['data'].status == 'COMPLETED'){
        this.displayMessage = 'Booking Completed Succesfully'
      }
      else {
      this.displayMessage = 'Booking Confirmed'
      }
    }
    else{
      this.displayMessage = 'Booking Failed.. Please Contanct Customer Care for further information'
    }
  }

  async downloadBooking() {
    this.toastrService.success("Started Downloading InVoice", "");
    this.mapLoaderActive = true;
    let endpoint = "booking_invoice/" + this.orderId;
    let result = await this.waterDataService.downloadPDF(endpoint).toPromise();
    console.log("result", result);
    this.mapLoaderActive = false;
  }

}
