import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ConstantsModule } from 'src/app/shared/constants.module';
import { SharedService } from 'src/app/shared/shared.service';
import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { LocalStorageService } from 'angular-web-storage';

@Component({
  selector: 'app-finish-booking',
  templateUrl: './finish-booking.component.html',
  styleUrls: ['./finish-booking.component.css']
})
export class FinishBookingComponent implements OnInit {

  errorMessage = ""
  displayErrorMessage = false
  bookingId;
  last4Digits;
  mapLoaderActive = false;
  bookingDetailsEnter = false;
  bookingInfo = {}
  milesDriven = {}
  finishDate;
  minDate;
  format;

  validation: boolean[] = new Array(3).fill(true);

  constructor(private localStorage: LocalStorageService, private route: ActivatedRoute, private router: Router, private waterDataService : SharedService, private constantsModule:ConstantsModule, private toastrService: ToastrService) { }

  ngOnInit() {
    this.minDate = new Date();
    this.format = "yyyyMMdd";
  }

  isNullOrUndefinedOrEmpty(val){
      return val == null || val == undefined || val == '' 
  }
  
  isNotNullOrUndefined(val){
    return val != null && val != undefined
  }

  async fetchBooking(){
      this.displayErrorMessage = false
      this.errorMessage = ""
      if(this.isNullOrUndefinedOrEmpty(this.bookingId)){
        this.errorMessage = "Booking Id is Empty"
        this.displayErrorMessage = true
        return;
      }

      this.mapLoaderActive = true;
      let result = await this.waterDataService.get("booking/" + this.bookingId).toPromise();
      console.log("\n fetchBooking ",result);
      this.mapLoaderActive = false;
      if(result != null && result != undefined){
        if(result['status'] == this.constantsModule.HTTP_STATUS.SUCCESS){
          let bookingInfo =result['bookingInfo'];
          if (!bookingInfo || !this.isNotNullOrUndefined(bookingInfo.bookingInfoId)) {
            this.errorMessage = "Booking Info Not Found"
            this.displayErrorMessage = true
            return;
          }
          else{
            if(bookingInfo.status == "CANCELLED"){
              this.errorMessage = "Booking Is already cancelled"
              this.displayErrorMessage = true
              return;
            }
            else if(bookingInfo.status == "COMPLETED"){
              this.errorMessage = "Booking is already finished"
              this.displayErrorMessage = true
              return;
            }
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
      console.log("\n this.bookingInfo \n", result['bookingInfo'])
      this.bookingDetailsEnter = true
      this.bookingInfo = result['bookingInfo'];
      return;
  }

  setBookingInfo(bookingInfo) {
    this.localStorage.set('bookingInfo', {
      data: bookingInfo
    });
  }

  async finishBooking(){
    let variantInfo = []
    for(let cartEntry of this.bookingInfo['cartList']){
      let miles = this.milesDriven[cartEntry.variant.variantId]
      if(this.isNullOrUndefinedOrEmpty(miles)){
        this.errorMessage = "Please Enter Miles Driven For " + cartEntry.variant.variantName
        this.displayErrorMessage = true
        return;
      }
      variantInfo.push({
        "varaintId" : cartEntry.variant.variantId,
		    "variantKilometersDriven" : miles
      })
    }
    let result = await this.waterDataService.post({bookingId:this.bookingId,variantInfoList:variantInfo,closingDate:this.finishDate.getTime()},"finish_booking").toPromise();
    console.log("\n finish_booking ",result);
    this.mapLoaderActive = false;
    if(result != null && result != undefined){
      if(result['status'] == this.constantsModule.HTTP_STATUS.SUCCESS){
          this.toastrService.success("Finished Booking Succesfully")
          this.bookingInfo['status'] = "COMPLETED"
          this.setBookingInfo(this.bookingInfo)
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
