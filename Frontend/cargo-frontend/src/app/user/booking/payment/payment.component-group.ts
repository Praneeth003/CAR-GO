import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LocalStorageService } from 'angular-web-storage';
import { ToastrService } from 'ngx-toastr';
import { ConstantsModule } from 'src/app/shared/constants.module';
import { SharedService } from 'src/app/shared/shared.service';
import { UtilsModule } from 'src/app/shared/utils/utils/utils.module';

@Component({
  selector: 'app-payment1',
  templateUrl: './payment.component-group.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent1 implements OnInit {

  disp = false;
  errorDisp = false;
  mapLoaderActive = false;

  dispPay = false;
  errorDispPay = false;

  validation ={};

  userProfileList = [];
  paymentInfoList = [];

  cartResponse = {};
  cartArr=[];

  combinedList = [];

  selectedUserProfile = {};
  selectedPaymentInfo = {};

  userProfileForCart ={

  };
  username: any;
  mNo: any;

  username1: any;
  mNo1: any;
  cvv: any;

  userId=null;

  totalPrice=0;
  taxPrice =0;
  totalPromoPrice=0;
  finalPrice=0;

  promoCodeList =[];
  
  selectedPromoCode =null;
  promoCode =null;

  title ={};

  constructor(private http: HttpClient, private waterDataService: SharedService, private constantsModule: ConstantsModule,
    private utils: UtilsModule, private router: Router, private localStorage: LocalStorageService, private toastrService: ToastrService) { }

  ngOnInit() {
    this.totalPrice =0;
    this.title = {};
    this.mapLoaderActive = true;
    let data = this.localStorage.get('cartResponse');
    this.selectedPaymentInfo =null;
    this.selectedPromoCode  =null;
    console.log("\n cartResponse data ", data);
    if (data == null || data == undefined || data['data'] == undefined) {
      this.router.navigate(["/user/journey-details"]);
    } else {
      this.cartResponse = data['data'];
      for(let v in this.cartResponse){
        this.title = this.cartResponse[v];
        break;
      }
    }
    console.log("\n cartResponse  this.title ",  this.title);
    let cartList =[];
    for(let variantId in this.cartResponse){
        this.userId = this.cartResponse[variantId]['userId'];
        this.cartResponse[variantId]["isVisible"] = false;
        this.totalPrice +=  this.cartResponse[variantId]['price'];
        this.userProfileForCart[variantId] ={};
        this.validation[variantId] =true;
        cartList.push(variantId);
    }


    this.taxPrice = this.totalPrice*0.1;
    this.finalPrice = this.totalPrice + this.taxPrice;
    this.cartArr = cartList;
    console.log("\n this.cartResponse ,this.userId,  this.totalPrice ,  this.cartArr ", this.cartResponse,this.userId ,  this.totalPrice,  this.cartArr );
    this.getUserProfile();
    this.getPaymentInfo();
    this.getPromoCodes();
  }

  checkPromoCode(){
    this.finalPrice = this.totalPrice + this.taxPrice;
    let valid = false;
    this.selectedPromoCode  =null;
    if(this.promoCode == null || this.promoCode == undefined){
     return;
    }
    let index = this.promoCodeList.findIndex(src => src['promoCodeName'] == this.promoCode);
    if(index != -1){
      valid = true;
      this.selectedPromoCode = this.promoCodeList[index];
    }
    console.log("\n  this.promoCode, this.promoCodeList this.selectedPromoCode ", this.promoCode,this.promoCodeList, this.selectedPromoCode);
    if(!valid){
      this.toastrService.error("Entered Prom Code is not Valid", "");
    }else{
      this.toastrService.success("Succesfully applied Promo Code Updating the Price...", "");
      this.getPriceWithPromoCode();
    }
  }

  async getPriceWithPromoCode(){
    let endPoint = 'promo_price';
    this.mapLoaderActive = true;
    let reqData = {
      "promo": this.selectedPromoCode,
      "totalAmount": this.totalPrice+this.taxPrice
    };
    console.log("\n promo_price reqData ", reqData);
    let result = await this.waterDataService.post(reqData, endPoint).toPromise();
    console.log("\n promo_price result ", result);
    this.mapLoaderActive = false;
    if (result != null && result != undefined) {
      if (result['status'] == this.constantsModule.HTTP_STATUS.SUCCESS && result['promoPrice'] != undefined) {
        this.totalPromoPrice= result['promoPrice'];
        this.finalPrice = this.totalPrice + this.taxPrice -this.totalPromoPrice;
      } else {
        this.toastrService.error("Alas there seems an issue try again later", "");
      }
    }
    this.mapLoaderActive = false;
  }

  async getPromoCodes(){
    this.mapLoaderActive = true;
    this.dispPay = false;
    this.errorDispPay = false;
    let endpoint = "promo_code";
    let result = await this.waterDataService.get(endpoint).toPromise();
    if (result != null && result != undefined) {
      let sList = result['promoCodeList'];
      if (sList && sList.length > 0) {
        this.promoCodeList = sList;
        this.dispPay = true;
        console.log("\n this.promoCodeList  ", this.promoCodeList);
      } else {
        this.errorDispPay = true;
      }
    } else {
      this.errorDispPay = true;
    }
    this.combinedList[2] = {
      "name": "Final Price Break",
      "isVisible": true,
      "id": "priceBreakUp"
    };
    this.mapLoaderActive = false;
  }

  onCartProfileChange(vId){
    console.log("\n onCartProfileChange vId, userProfileForCart ",vId,this.userProfileForCart);
    for(let variantId in this.cartResponse){
      if(vId != variantId){
        let profileList = this.cartResponse[variantId]['profileList'];
        let index = profileList.findIndex(src => src['userProfileId'] == this.userProfileForCart[vId]['userProfileId']);
        console.log("\n index ",index);
        if (index != -1) {
          profileList[index]['isAvailable'] = false;
        }else{
          profileList[index]['isAvailable'] = true;
        }
        this.cartResponse[variantId]['profileList'] = null;
        this.cartResponse[variantId]['profileList'] = profileList;
      }
    }
    console.log("\n onCartProfileChange  this.cartResponse", this.cartResponse);
  }

  setActiveClassForCartProfile(variantId) {
    console.log("\n setActiveClassForCartProfile variantId", variantId);
    this.cartResponse[variantId]["isVisible"] = !this.cartResponse[variantId]["isVisible"];
  }


  async getUserProfile() {
    this.mapLoaderActive = true;
    this.disp = false;
    this.errorDisp = false;
    let endpoint = "user_profile/" + this.userId;
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
    for(let variantId in this.cartResponse){
      this.cartResponse[variantId]['profileList'] =[];
      for(let profile of this.userProfileList){
        profile['isAvailable'] = true;
        this.cartResponse[variantId]['profileList'].push(profile);
      }
    }
    console.log("\n  this.cartResponse  ",  this.cartResponse);
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
    let endpoint = "payment_info/" + this.userId;
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
      "userId": this.userId,
      "userProfileName": this.username,
      "licenceNumber": this.mNo
    };
    console.log("\n addToCartAPI reqData ", reqData);
    let result = await this.waterDataService.post(reqData, endPoint).toPromise();
    console.log("\n addToCartAPI result ", result);
    this.mapLoaderActive = false;
    if (result != null && result != undefined) {
      if (result['status'] == this.constantsModule.HTTP_STATUS.SUCCESS && result['userProfileList'] != undefined && result['userProfileList'].length > 0) {
        this.userProfileList = null;
        // this.userProfileList.push();
        let resProfile = result['userProfileList'][0];
        resProfile['isAvailable'] = true;
        // this.getUserProfile();
        for(let variantId in this.cartResponse){
          this.cartResponse[variantId]['profileList'].push(resProfile);
        }
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
      "userId": this.userId,
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

  validateProfileInfoForCart(){
    let bool = true;
    for(let variantId in this.cartResponse){
      if(this.userProfileForCart[variantId] && this.userProfileForCart[variantId]['userProfileId']){
        this.validation[variantId] = true;
      }else{
        this.validation[variantId] = false;
        bool =false;
      }
    }
    return bool;

  }

  calculateVariantPrice(cartData){
    let vPrice =null;
    let price =null;
    console.log("\n calculateVariantPrice cartData",cartData);
    if(cartData['cartPrice'] && cartData['cartPrice']['addOnPrices'] 
    &&cartData['cartPrice']['addOnPrices'].length >0){
      price =0;
      for(let addOn of cartData['cartPrice']['addOnPrices']){
        console.log("\n  addOn ",addOn );
        price += addOn['price'];
      }
    }

    console.log("\n calculateVariantPrice price",price);
    if( cartData['cartPrice'] && cartData['cartPrice']['price']){
      if(price !=null){
        vPrice = cartData['cartPrice']['price'] -price;
      }else{
        vPrice = cartData['cartPrice']['price'];
      }
    }

    console.log("\n calculateVariantPrice vPrice",vPrice);

    return vPrice == null ? "-":vPrice;

  }

  calculateAddOnPrice(cartData){
    let price =null;
    console.log("\n calculateAddOnPrice cartData",cartData);
    if(cartData['cartPrice'] && cartData['cartPrice']['addOnPrices'] 
    &&cartData['cartPrice']['addOnPrices'].length >0){
      price =0;
      for(let addOn of cartData['cartPrice']['addOnPrices']){
        console.log("\n  addOn ",addOn );
        price += addOn['price'];
      }
    }
    return price == null ? "-":price;
  }

  payNow() {
    let bool = this.validateProfileInfoForCart();
    if(!bool){
      this.toastrService.error("Please Select License/Profile for all the Cart Items to proceed ", "");
      return;
    }
    if (this.selectedPaymentInfo == null || this.userId == null) {
      this.toastrService.error("Please Select Payment Information to Book ", "");
      return;
    }
    this.confirmBooking();
  }

  async confirmBooking() {
    let endPoint = 'create_booking';
    this.mapLoaderActive = true;
    let cartProfileId = [];
    let cartArr1 =[];
    for(let variantId in this.cartResponse){
      cartArr1.push(this.cartResponse[variantId]['cartId']);
      cartProfileId.push(this.userProfileForCart[variantId]['userProfileId']);
    }
    let reqData = {
      "userId": this.userId,
      "paymentMethodInfoId": this.selectedPaymentInfo['paymentInfoId'],
      "cartIds": cartArr1,
      "userProfileIds": cartProfileId
    };
    if(this.selectedPromoCode != null){
      reqData['promoCodeIds'] =[this.selectedPromoCode['promoCodeId']];
    }
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


