import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from 'angular-web-storage';
import { ToastrService } from 'ngx-toastr';
import { ConstantsModule } from 'src/app/shared/constants.module';
import { SharedService } from 'src/app/shared/shared.service';
import { UtilsModule } from 'src/app/shared/utils/utils/utils.module';

@Component({
  selector: 'app-booking-history',
  templateUrl: './booking-history.component.html',
  styleUrls: ['./booking-history.component.css']
})
export class BookingHistoryComponent implements OnInit {

  userData =null;
  bookingInfoList =[];
  mapLoaderActive: boolean;
  dispPay: boolean;
  errorDispPay: boolean;
  constructor(private http: HttpClient, private waterDataService: SharedService, private constantsModule: ConstantsModule,
    private utils: UtilsModule, private router: Router, private localStorage: LocalStorageService, private toastrService: ToastrService) { }

  
  ngOnInit() {
    this.getAndSetUserDetails();
  }

  
  initializeVariables(){
    this.bookingInfoList =[];
  }

  getAndSetUserDetails(){
    let data = this.localStorage.get('userDetails');
    console.log("\n userDetails data ", data);
    if (data != null && data != undefined && data['data'] != undefined) {
      this.userData = data['data'];
    }
    if(this.userData == null || this.userData['userId'] == null || this.userData['userId'] == undefined){
      this.navigateRelHome();
    }else{
      this.triggerFunctions();
    }
  }

  triggerFunctions(){
    this.initializeVariables();
    this.getBookingHistory();
  }


  async getBookingHistory(){
    this.mapLoaderActive = true;
    this.dispPay = false;
    this.errorDispPay = false;
    let endpoint = "booking_history/"+this.userData['userId'];
    let result = await this.waterDataService.get(endpoint).toPromise();
    if (result != null && result != undefined) {
      let sList = result['bookingInfoList'];
      if (sList && sList.length > 0) {
        this.bookingInfoList = sList;
        this.dispPay = true;
        console.log("\n this.bookingInfoList  ", this.bookingInfoList);
      } else {
        this.errorDispPay = true;
      }
    } else {
      this.toastrService.error("Error in Loading History Data", "");
    }
    this.mapLoaderActive = false;
  }

  // rowClick(booking){
  //   console.log("\n rowClick booking ",booking);
  // }

  upgradeBooking(booking){
    console.log("\n upgradeBooking booking ",booking);
    this.localStorage.set('bookingHistory', {
      data: booking
    });
    this.router.navigate(["/user/upgradeBooking"]);
  }

  
  viewBooking(booking){
    console.log("\n viewBooking booking ",booking);
  }

  cancelBooking(booking){
    console.log("\n cancelBooking booking ",booking);
    let confirmResponse = confirm("Do you want to cance the booking for sure")
    if(confirmResponse) {
      this.cancelBookingWithId(booking['bookingInfoId'])
    }
  }

  async cancelBookingWithId(bookingId){
    let result = await this.waterDataService.get("cancel_booking/" + bookingId).toPromise();
    console.log("\n cancel_booking ",result);
    this.mapLoaderActive = false;
    if(result != null && result != undefined){
      if(result['status'] == this.constantsModule.HTTP_STATUS.SUCCESS){
          this.toastrService.success("Cancelled Booking Succesfully");
          this.triggerFunctions();
          // this.router.navigate(["/user/booking-preview"]);
      }
      else{
        this.toastrService.error("Cancelled Booking Failed Please Contact Support Team");
        return;
      }
    }
  }

  async downloadInvoice(booking) {
    console.log("\n downloadInvoice booking ",booking);
    this.toastrService.success("Started Downloading InVoice", "");
    this.mapLoaderActive = true;
    let endpoint = "booking_invoice/" +booking['bookingInfoId'];
    let result = await this.waterDataService.downloadPDF(endpoint).toPromise();
    console.log("result", result);
    this.mapLoaderActive = false;
  }

  isUpdateEnabled(booking){
    console.log("\n isUpdateEnabled booking ",booking);
    if(booking['status'] == "SUCCESS"){
      return true;
    }
    return false;
  }

  isCancelEnabled(booking){
    console.log("\n isCancelEnabled booking ",booking);
    if(booking['status'] == "SUCCESS"){
      return true;
    }
    return false;
  }


  getBookingFromDate(booking){
    console.log("\n getBookingFromDate booking ",booking,new Date());
    let sDate = booking['cartList'][0]['fromDate'].toString().substring(0, 10).replaceAll("-","");
    let formatted =   this.utils.getDateInFormatFromModelDate(sDate, 'mmm dd, yyyy');
    console.log("\n sDate ",sDate," booking['cartList'][0]['fromDate'] ",booking['cartList'][0]['fromDate']," formatted ",formatted); 
    return formatted;

  }

  getBookingToDate(booking){
    console.log("\n getBookingToDate booking ",booking);
    let sDate = booking['cartList'][0]['toDate'].toString().substring(0, 10).replaceAll("-","");
    let formatted =   this.utils.getDateInFormatFromModelDate(sDate, 'mmm dd, yyyy');
    console.log("\n sDate ",sDate," booking['cartList'][0]['toDate'] ",booking['cartList'][0]['toDate']," formatted ",formatted); 
    return formatted;
  }



  navigateRelHome() {
    let journeyDetailsFilter = this.localStorage.get('journeyDetailsFilter');
    console.log("\n journeyDetailsFilter data ", journeyDetailsFilter);
    if (journeyDetailsFilter != null && journeyDetailsFilter != undefined && journeyDetailsFilter['data'] != undefined) {
      this.router.navigate(["/user/home"]);
    } else {
      this.router.navigate(["/user/journey-details"]);
    }
  }

  async downloadBookingInvoice() {
    this.toastrService.success("Started Downloading InVoice", "");
    this.mapLoaderActive = true;
    let endpoint = "booking_invoice/" + this.orderId;
    let result = await this.waterDataService.downloadPDF(endpoint).toPromise();
    console.log("result", result);
    this.mapLoaderActive = false;

  }

  navigate() {
    let journeyDetailsFilter = this.localStorage.get('journeyDetailsFilter');
    console.log("\n journeyDetailsFilter data ", journeyDetailsFilter);
    if (journeyDetailsFilter != null && journeyDetailsFilter != undefined && journeyDetailsFilter['data'] != undefined) {
      this.router.navigate(["/user/home"]);
    } else {
      this.router.navigate(["/user/journey-details"]);
    }
  }

}
