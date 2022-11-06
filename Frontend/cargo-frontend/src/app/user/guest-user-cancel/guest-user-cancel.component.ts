import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LocalStorageService } from 'angular-web-storage';
import { ToastrService } from 'ngx-toastr';
import { ConstantsModule } from 'src/app/shared/constants.module';
import { SharedService } from 'src/app/shared/shared.service';

@Component({
  selector: 'app-guest-user-cancel',
  templateUrl: './guest-user-cancel.component.html',
  styleUrls: ['./guest-user-cancel.component.css']
})
export class GuestUserCancelComponent implements OnInit {

  errorMessage = ""
  displayErrorMessage = false
  bookingId;
  last4Digits;
  mapLoaderActive = false;

  constructor(private localStorage: LocalStorageService, private route: ActivatedRoute, private router: Router, private waterDataService : SharedService, private constantsModule:ConstantsModule, private toastrService: ToastrService) { }

  ngOnInit() {
  }

  isNullOrUndefinedOrEmpty(val){
      return val == null || val == undefined || val == '' 
  }
  
  isNotNullOrUndefined(val){
    return val != null && val != undefined
  }

  async cancelBooking(){
      this.displayErrorMessage = false
      this.errorMessage = ""
      if(this.isNullOrUndefinedOrEmpty(this.bookingId)){
        this.errorMessage = "Booking Id is Empty"
        this.displayErrorMessage = true
        return;
      }

      if(this.isNullOrUndefinedOrEmpty(this.last4Digits) || this.last4Digits.length < 4){
        this.errorMessage = "Enter Minimum four last digits of ur licence number"
        this.displayErrorMessage = true
        return;
      }

      this.mapLoaderActive = true;
      let result = await this.waterDataService.post({bookingInfoId:this.bookingId,licenseLast4Digits:this.last4Digits},"find_booking").toPromise();
      console.log("\n find_booking ",result);
      this.mapLoaderActive = false;
      if(result != null && result != undefined){
        if(result['status'] == this.constantsModule.HTTP_STATUS.SUCCESS){
          let bookingInfo =result['bookingInfo'];
          if (!bookingInfo || !this.isNotNullOrUndefined(bookingInfo.bookingInfoId)) {
            this.errorMessage = "Booking Info Not Found"
            this.displayErrorMessage = true
            return;
          }
        }
        else{
          this.errorMessage = result["errorDescription"]
          this.displayErrorMessage = true
          return;
        }
      }
      else {
        this.errorMessage = "Booking Info Not Found"
        this.displayErrorMessage = true
        return;
      }
      let confirmResponse = confirm("Do you want to cance the booking for sure")
      if(confirmResponse) {
        this.setBookingInfo(result['bookingInfo']);
        console.log("\n this.bookingInfo \n", result['bookingInfo']);
        this.cancelBookingWithId(this.bookingId)
      }
      
      return;
  }

  setBookingInfo(bookingInfo) {
    this.localStorage.set('bookingInfo', {
      data: bookingInfo
    });
  }

  async cancelBookingWithId(bookingId){
    let result = await this.waterDataService.get("cancel_booking/" + this.bookingId).toPromise();
    console.log("\n cancel_booking ",result);
    this.mapLoaderActive = false;
    if(result != null && result != undefined){
      if(result['status'] == this.constantsModule.HTTP_STATUS.SUCCESS){
          this.toastrService.success("Cancelled Booking Succesfully")
          this.router.navigate(["/user/booking-preview"]);
      }
      else{
        this.errorMessage = "Unable to cancel the booking.. Try the support team"
        this.displayErrorMessage = true
        return;
      }
    }
  }

}
