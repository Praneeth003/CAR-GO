import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LocalStorageService } from 'angular-web-storage';
import { ToastrService } from 'ngx-toastr';
import { ConstantsModule } from 'src/app/shared/constants.module';
import { SharedService } from 'src/app/shared/shared.service';
import { UtilsModule } from 'src/app/shared/utils/utils/utils.module';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component-new.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {

  disp = false;
  errorDisp = false;
  mapLoaderActive = false;

  dispPay = false;
  errorDispPay = false;

  userProfileList = [];
  paymentInfoList = [];
  cartResponse = {};

  combinedList = [];

  selectedUserProfile = {};
  selectedPaymentInfo = {};

  username: any;
  mNo: any;

  username1: any;
  mNo1: any;
  cvv: any;

  totalPrice=0;

  constructor(private http: HttpClient, private waterDataService: SharedService, private constantsModule: ConstantsModule,
    private utils: UtilsModule, private router: Router, private localStorage: LocalStorageService, private toastrService: ToastrService) { }

  ngOnInit() {
    this.mapLoaderActive = true;
    let data = this.localStorage.get('selectedCartResponse');
    console.log("\n journeyDetailsFilter data ", data);
    if (data == null || data == undefined || data['data'] == undefined) {
      this.router.navigate(["/user/journey-details"]);
    } else {
      this.cartResponse = data['data'];
    }

    console.log("\n this.cartResponse ", this.cartResponse);
    this.getUserProfile();
    this.getPaymentInfo();

  }



  async getUserProfile() {
    this.mapLoaderActive = true;
    this.disp = false;
    this.errorDisp = false;
    let endpoint = "user_profile/" + this.cartResponse['userId'];
    let result = await this.waterDataService.get(endpoint).toPromise();
    if (result != null && result != undefined) {
      let sList = result['userProfileList'];
      if (sList && sList.length > 0) {
        this.userProfileList = sList;
        this.disp = true;
        console.log("\n this.userProfileList  ", this.userProfileList);
      } else {
        this.errorDisp = true;
      }
    } else {
      this.errorDisp = true;
    }
    this.combinedList[0] = {
      "name": "Existing User Profile",
      "data": this.userProfileList,
      "isVisible": true,
      "id": "profile"
    };
    this.mapLoaderActive = false;
  }

  async getPaymentInfo() {
    this.mapLoaderActive = true;
    this.dispPay = false;
    this.errorDispPay = false;
    let endpoint = "payment_info/" + this.cartResponse['userId'];
    let result = await this.waterDataService.get(endpoint).toPromise();
    if (result != null && result != undefined) {
      let sList = result['paymentInfoList'];
      if (sList && sList.length > 0) {
        this.paymentInfoList = sList;
        this.dispPay = true;
        console.log("\n this.paymentInfoList  ", this.paymentInfoList);
      } else {
        this.errorDispPay = true;
      }
    } else {
      this.errorDispPay = true;
    }
    this.combinedList[1] = {
      "name": "Existing Payment Information",
      "data": this.paymentInfoList,
      "isVisible": true,
      "id": "payment"
    };
    this.mapLoaderActive = false;
  }

  checkAddUserProfile() {
    if (this.mNo == null || this.username == null || this.mNo == undefined || this.username == undefined) {
      this.toastrService.error("Please Enter Valid fields for both Profile Name and Licence Number", "");
      return;
    }
    this.addUserProfile();
  }

  async addUserProfile() {
    let endPoint = 'user_profile';
    this.mapLoaderActive = true;
    let reqData = {
      "userId": this.cartResponse['userId'],
      "userProfileName": this.username,
      "licenceNumber": this.mNo
    };
    console.log("\n addToCartAPI reqData ", reqData);
    let result = await this.waterDataService.post(reqData, endPoint).toPromise();
    console.log("\n addToCartAPI result ", result);
    this.mapLoaderActive = false;
    if (result != null && result != undefined) {
      if (result['status'] == this.constantsModule.HTTP_STATUS.SUCCESS && result['userProfileList'] != undefined && result['userProfileList'].length > 0) {
        // this.userProfileList = null;
        this.userProfileList.push(result['userProfileList'][0]);
        console.log("\n this.userProfileList \n", this.userProfileList);
        this.toastrService.success("You have successfully added User Profile", "");
        this.username = null;
        this.mNo = null;
      } else {
        this.toastrService.error("Alas there seems an issue try again later" + result.errorDescription, "");
      }
    }
    this.mapLoaderActive = false;
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
      "userId": this.cartResponse['userId'],
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

  setActiveClass(item) {
    console.log("\n setActiveClass ", item);
    let index = this.combinedList.findIndex(src => src['id'] == item['id']);
    if (index != -1) {
      this.combinedList[index]['isVisible'] = !this.combinedList[index]['isVisible'];
    }
  }

  onProfileChange(event, item) {
    console.log("\n onBodyTypeChange item", item, "event", event, " selectedFilterParams ", this.selectedUserProfile);


    let index = -1;
    index = this.combinedList[0]['data'].findIndex(src => src['userProfileId'] == item['userProfileId']);

    if (event.target.checked) {
      this.selectedUserProfile = item;
      if (index !== -1) {
        for (let i = 0; i < this.combinedList[0]['data'].length; i++) {
          if (i != index) {
            this.combinedList[0]['data'][i]['isChecked'] = false;
          } else {
            this.combinedList[0]['data'][index]['isChecked'] = true;
          }
        }
      }
    } else {
      if (index !== -1) {
        this.combinedList[0]['data'][index]['isChecked'] = false;
        this.selectedUserProfile = null;
      }
    }

    console.log("\n selectedUserProfile ", this.selectedUserProfile);
  }


  onPaymentInfoChange(event, item) {
    console.log("\n onBodyTypeChange item", item, "event", event, " selectedFilterParams ", this.selectedPaymentInfo);

    let index = -1;
    index = this.combinedList[1]['data'].findIndex(src => src['paymentInfoId'] == item['paymentInfoId']);

    if (event.target.checked) {

      this.selectedPaymentInfo = item;

      if (index !== -1) {
        for (let i = 0; i < this.combinedList[1]['data'].length; i++) {
          if (i != index) {
            this.combinedList[1]['data'][i]['isChecked'] = false;
          } else {
            this.combinedList[1]['data'][index]['isChecked'] = true;
          }
        }
      } else {
        if (index !== -1) {
          this.combinedList[1]['data'][index]['isChecked'] = false;
          this.selectedPaymentInfo = null;
        }
      }

    }


    console.log("\n selectedPaymentInfo ", this.selectedPaymentInfo);
  }

  payNow() {
    if (this.selectedPaymentInfo == null || this.selectedUserProfile == null || this.cartResponse['userId'] ==null) {
      this.toastrService.error("Please Select atleast one of both User Profile and Payment Information to Book ", "");
      return;
    }
    this.confirmBooking();
  }

  async confirmBooking() {
    let endPoint = 'create_booking';
    this.mapLoaderActive = true;
    let reqData = {
      "userId": this.cartResponse['userId'],
      "paymentMethodInfoId": this.selectedPaymentInfo['paymentInfoId'],
      "cartIds": [this.cartResponse['cartId']],
      "userProfileIds": [this.selectedUserProfile['userProfileId']]
    };
    console.log("\n addToCartAPI reqData ", reqData);
    let result = await this.waterDataService.post(reqData, endPoint).toPromise();
    console.log("\n addToCartAPI result ", result);
    this.mapLoaderActive = false;
    if (result != null && result != undefined) {
      if (result['status'] == this.constantsModule.HTTP_STATUS.SUCCESS && result['bookingInfo'] != undefined && result['bookingInfo']['bookingInfoId'] != undefined) {
        let bookingInfo = result['bookingInfo'];
        this.setBookingInfo(bookingInfo);
        console.log("\n this.bookingInfo \n", bookingInfo);
        this.toastrService.success("You have successfully Created Booking", "");
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


