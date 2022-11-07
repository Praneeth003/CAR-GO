import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { LocalStorageService } from 'angular-web-storage';
import { ToastrService } from 'ngx-toastr';
import { ConstantsModule } from 'src/app/shared/constants.module';
import { DatepickerComponent } from 'src/app/shared/datepicker/datepicker.component';
import { SharedService } from 'src/app/shared/shared.service';
import { UtilsModule } from 'src/app/shared/utils/utils/utils.module';

@Component({
  selector: 'app-booking-upgrade',
  templateUrl: './booking-upgrade.component.html',
  styleUrls: ['./booking-upgrade.component.css']
})
export class BookingUpgradeComponent implements OnInit {

  @ViewChild(DatepickerComponent) datepicker: DatepickerComponent;

  mapLoaderActive: boolean;
  dispPay: boolean;
  errorDispPay: boolean;
  booking=null;

  updatedBooking =null;

  toggleClass ={
    bookingDetails:true,
    upgradeBookingDetails:false,
    finishUpgrade:false
  }

  fromModel: NgbDateStruct;
  toModel: NgbDateStruct;

  validation: boolean[] = new Array(3).fill(true);

  minDate: Date;
  maxDate: Date;

  cartList =[];
  updatedCartList =[];
  title={};
  updatedTitle ={};
  paymentInfoList=[];

  priceDifference =0;

  showPaymentSection= false;
  showUpdatedCartList =false;
  username1: any;
  mNo1: any;
  cvv: any;
  selectedPaymentInfo = {};
  selectedFilterParams = {
    fromLocation: {},
    toLocation: {},
    format: null,
    fsDate: { value: null },
    feDate: { value: null },
    sDate: { value: null },
    eDate: { value: null },
    fromDate:null,
    toDate:null
  }

  constructor(private http: HttpClient, private waterDataService: SharedService, private constantsModule: ConstantsModule,
    private utils: UtilsModule, private router: Router, private localStorage: LocalStorageService, private toastrService: ToastrService) { }

  ngOnInit() {
    this.booking =null;
    this.updatedBooking =null;
    this.priceDifference =0;
    this.showPaymentSection =false;
    this.showUpdatedCartList =false;
    this.minDate = new Date();
    this.selectedPaymentInfo = {};
    this.username1 =null;
    this.mNo1 =null;
    this.cvv =null;
    this.cartList =[];
    this.paymentInfoList =[];
    this.updatedCartList =[];
    this.selectedFilterParams.format = "yyyyMMdd";
    this.getAndSetBookingData();
  }

  setActiveClass(id){
    this.toggleClass[id] = !this.toggleClass[id];
    for(let item in this.toggleClass){
      if(item != id){
        this.toggleClass[item] =false;
      }
    }
  }



  getAndSetBookingData(){
    let data = this.localStorage.get('bookingHistory');
    console.log("\n getAndSetBookingData data ", data);
    if (data != null && data != undefined && data['data'] != undefined) {
      this.booking = data['data'];
      this.cartList = this.booking['cartList'];
      this.title = this.cartList[0];
      // this.selectedFilterParams.sDate.value = this.utils.getModelDateWithDateFormat(this.booking['cartList'][0]['fromDate'], this.selectedFilterParams.format);
      // this.selectedFilterParams.eDate.value = this.utils.getModelDateWithDateFormat(this.booking['cartList'][0]['toDate'], this.selectedFilterParams.format);

    }
    if(this.booking == null || this.booking['bookingInfoId'] == null || this.booking['bookingInfoId'] == undefined){
      this.navigateRelHome();
    }
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


  getBookingFromDate(booking){
    console.log("\n getBookingFromDate booking ",booking,new Date());
    let sDate = booking['fromDate'].toString().substring(0, 10).replaceAll("-","");
    let formatted =   this.utils.getDateInFormatFromModelDate(sDate, 'mmm dd, yyyy');
    console.log("\n sDate ",sDate,"  booking['fromDate'", booking['fromDate']," formatted ",formatted); 
    return formatted;

  }

  getBookingToDate(booking){
    console.log("\n getBookingToDate booking ",booking);
    let sDate = booking['toDate'].toString().substring(0, 10).replaceAll("-","");
    let formatted =   this.utils.getDateInFormatFromModelDate(sDate, 'mmm dd, yyyy');
    console.log("\n sDate ",sDate," booking['toDate'] ",booking['toDate']," formatted ",formatted); 
    return formatted;
  }

  
  isValid(): boolean {
    this.validation[0] = this.selectedFilterParams.sDate['value'] != null && this.selectedFilterParams.sDate['value'] != undefined;
    this.validation[1] = this.selectedFilterParams.eDate['value'] != null && this.selectedFilterParams.eDate['value'] != null;
    if (this.validation[0] && this.validation[1]) {
      this.validation[2] = this.utils.getModelDateWithDateFormat(this.selectedFilterParams.sDate['value'], this.selectedFilterParams.format) >
        this.utils.getModelDateWithDateFormat(this.selectedFilterParams.eDate['value'], this.selectedFilterParams.format) ? false : true;
    }
    let valid = true;
    for (let ind = 0; ind < this.validation.length; ind++) {
      valid = valid && this.validation[ind];
    }
    console.log("\n In isValid ",valid);
    return valid;
  }

  upgradeBooking(){
    this.showUpdatedCartList =false;
    this.updatedCartList =[];
    this.showPaymentSection =false;
    this.paymentInfoList =[];
    this.selectedPaymentInfo = {};
    if(this.isValid()){
      console.log("\n Entered upgradeBooking");
      this.selectedFilterParams.fsDate.value = this.utils.getModelDateWithDateFormat(this.selectedFilterParams.sDate.value, this.selectedFilterParams.format);
      this.selectedFilterParams.feDate.value = this.utils.getModelDateWithDateFormat(this.selectedFilterParams.eDate.value, this.selectedFilterParams.format);
      this.selectedFilterParams.fromDate =this.selectedFilterParams.sDate.value;
      this.selectedFilterParams.toDate = this.selectedFilterParams.eDate.value;
      console.log("\n In upgradeBooking ",this.selectedFilterParams);
      this.getUpgradeBookingPrice();
    }else{
      this.toastrService.error("Please enter Valid Details"); 
    }
  }

  async getUpgradeBookingPrice(){

    console.log("\n getUpgradeBookingPrice",this.selectedFilterParams);
    this.mapLoaderActive = true;
    this.dispPay = false;
    this.errorDispPay = false;

    let endpoint = "update_booking";
    let reqData ={
      "bookingInfoId":this.booking['bookingInfoId'],
      "fromDate":new Date(this.selectedFilterParams.fromDate),
      "toDate": this.selectedFilterParams.toDate 
    };
    let result = await this.waterDataService.post(reqData,endpoint).toPromise();
    console.log("\n getUpgradeBookingPrice result",result);
    if (result != null && result != undefined && result['status'] == this.constantsModule.HTTP_STATUS.SUCCESS && result['bookingInfo'] != undefined) {      
      this.updatedBooking = result['bookingInfo'];
      this.updatedCartList = this.updatedBooking['cartList'];
      this.updatedTitle = this.updatedCartList[0];
      this.checkAndSetPaymentDetails();
    } else {
      this.toastrService.error("Error in Upgrade Booking", "");
    }
    console.log("\n getUpgradeBookingPrice  this.updatedBooking", this.updatedBooking);
    this.mapLoaderActive = false;
  }

  checkAndSetPaymentDetails(){
    this.priceDifference = this.updatedBooking['totalPrice']-this.booking['totalPrice']  ;
    console.log("\n checkAndSetPaymentDetails ",  this.priceDifference );
    this.showUpdatedCartList =true;
    this.toggleClass.upgradeBookingDetails = false;
    this.toggleClass.bookingDetails = false;
    this.toggleClass.finishUpgrade = true;
    if(this.priceDifference >0){
      this.getPaymentInfo();
    }

  }

  async getPaymentInfo() {
    this.mapLoaderActive = true;
    this.dispPay = false;
    this.errorDispPay = false;
    let endpoint = "payment_info/" + this.booking['user']['userId'];
    let result = await this.waterDataService.get(endpoint).toPromise();
    if (result != null && result != undefined) {
      let sList = result['paymentInfoList'];
      if (sList && sList.length > 0) {
        this.paymentInfoList = sList;
        this.dispPay = true;
        this.showPaymentSection =true;
        console.log("\n this.paymentInfoList  ", this.paymentInfoList);
      } else {
        this.errorDispPay = true;
      }
    } else {
      this.errorDispPay = true;
    }
    this.mapLoaderActive = false;
  }

  onPaymentInfoChange(event, item) {
    console.log("\n onBodyTypeChange item", item, "event", event, " selectedFilterParams ", this.selectedPaymentInfo);

    let index = -1;
    index = this.paymentInfoList.findIndex(src => src['paymentInfoId'] == item['paymentInfoId']);

    if (event.target.checked) {

      this.selectedPaymentInfo = item;

      if (index !== -1) {
        for (let i = 0; i < this.paymentInfoList.length; i++) {
          if (i != index) {
            this.paymentInfoList[i]['isChecked'] = false;
          } else {
            this.paymentInfoList[index]['isChecked'] = true;
          }
        }
      } else {
        if (index !== -1) {
          this.paymentInfoList[index]['isChecked'] = false;
          this.selectedPaymentInfo = null;
        }
      }

    }
  }


  checkAddPaymentInfo() {
    if (this.mNo1 == null || this.username1 == null || this.mNo1 == undefined || this.username1 == undefined
      || this.cvv == null || this.cvv == undefined) {
      this.toastrService.error("Please Enter Valid fields for both Card Holder Name and Card Number and Cvv Details", "");
      return;
    }
    this.addPaymentInfo();
  }

  async addPaymentInfo() {
    let endPoint = 'payment_info';
    this.mapLoaderActive = true;
    let reqData = {
      "userId": this.booking['user']['userId'],
      "paymentMethodInfo": {
        "cardNumber": this.mNo1,
        "nameOnCard": this.username1
      }
    };
    console.log("\n addToCartAPI reqData ", reqData);
    let result = await this.waterDataService.post(reqData, endPoint).toPromise();
    console.log("\n addToCartAPI result ", result);
    this.mapLoaderActive = false;
    if (result != null && result != undefined) {
      if (result['status'] == this.constantsModule.HTTP_STATUS.SUCCESS && result['paymentInfoList'] != undefined && result['paymentInfoList'].length > 0) {
        // this.paymentInfoList = null;
        // this.paymentInfoList = result['paymentInfoList'] ;
        this.paymentInfoList.push(result['paymentInfoList'][0]);
        console.log("\n this.paymentInfoList \n", this.paymentInfoList);
        this.toastrService.success("You have successfully added Payment Information", "");
        this.username1 = null;
        this.mNo1 = null;
        this.cvv = null;
      } else {
        this.toastrService.error("Alas there seems an issue try again later" + result.errorDescription, "");
      }
    }
    this.mapLoaderActive = false;
  }



  payNow() {
    if (this.updatedBooking ==null) {
      this.toastrService.error("Please update Journery Details for upgrading ", "");
      return;
    }
    if (this.showPaymentSection == true && this.selectedPaymentInfo['paymentInfoId'] == undefined ) {
      this.toastrService.error("Please Select Payment Information to Book ", "");
      return;
    }
    this.upgradeBookingPersist();
  }

  async upgradeBookingPersist() {
    let endPoint = 'update_booking_persist';
    this.mapLoaderActive = true;
    let reqData ={
      "bookingInfoId":this.booking['bookingInfoId'],
      "fromDate":new Date(this.selectedFilterParams.fromDate),
      "toDate": this.selectedFilterParams.toDate 
    };
    console.log("\n upgradeBookingPersist reqData ", reqData);
    let result = await this.waterDataService.post(reqData, endPoint).toPromise();
    console.log("\n addToCartAPI result ", result);
    this.mapLoaderActive = false;
    if (result != null && result != undefined) {
      if (result['status'] == this.constantsModule.HTTP_STATUS.SUCCESS && result['bookingInfo'] != undefined && result['bookingInfo']['bookingInfoId'] != undefined) {
        let bookingInfo = result['bookingInfo'];
        this.setBookingInfo(bookingInfo);
        console.log("\n this.bookingInfo \n", bookingInfo);
        this.toastrService.success("You have successfully Updated Booking", "");
        this.router.navigate(["/user/booking-preview"]);
      } else {
        this.toastrService.error("Alas there seems an issue try again later" + result.errorDescription, "");
      }
    }
    this.mapLoaderActive = false;
  }
  


  setBookingInfo(bookingInfo) {
    this.localStorage.set('bookingInfo', {
      data: bookingInfo
    });
  }

}
