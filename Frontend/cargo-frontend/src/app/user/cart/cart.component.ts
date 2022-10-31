import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LocalStorageService } from 'angular-web-storage';
import { ToastrService } from 'ngx-toastr';
import { ConstantsModule } from 'src/app/shared/constants.module';
import { SharedService } from 'src/app/shared/shared.service';
import { UtilsModule } from 'src/app/shared/utils/utils/utils.module';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  cartArr=[];
  cartData ={};
  mapLoaderActive;
  totalPrice;
  constructor(private http: HttpClient, private httpService: SharedService, private route: ActivatedRoute, private router: Router, private localStorage: LocalStorageService,
    private waterDataService: SharedService, private constantsModule: ConstantsModule,
    private utils: UtilsModule, private toastrService: ToastrService) { }

  ngOnInit() {
    this.triggerFunctions();
  }

  initiazeVariables(){
    this.cartArr=[];
    this.cartData ={};
    this.totalPrice = null;
  }

  triggerFunctions(){
    this.initiazeVariables();
    let data = this.localStorage.get('cartData');
    console.log("\n existing cartData ", data);
    if (data != null && data != undefined && data['data'] != undefined) {
      this.cartData = data['data'];
      console.log("\n this.cartData", this.cartData);
    } else {
      this.navigateRelHome();
    }
    this.addPriceToCart();
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

  async addPriceToCart() {
    let endPoint = 'cart_price';
    this.mapLoaderActive = true;
    let sumPrice =0;
    let cartList =[];
    for (let variantId in this.cartData) {
      cartList.push(variantId);
      let cartEntry = this.cartData[variantId];
      let priceResponse = null;
      let reqData = cartEntry['postData'];
      console.log("\n addPriceToCart cartEntry ", cartEntry);
      console.log("\n addPriceToCart reqData ", reqData);
      let result = await this.waterDataService.post(reqData, endPoint).toPromise();
      console.log("\n addPriceToCart result ", result);
      if (result['status'] == this.constantsModule.HTTP_STATUS.SUCCESS && result['cartPrice'] != undefined && result['cartPrice']['price'] != undefined) {
        priceResponse = result['cartPrice'];
        this.cartData[variantId]['priceResponse'] = priceResponse;
        sumPrice += priceResponse['price'];
        console.log("\n addPriceToCart priceResponse \n", priceResponse,"\n  this.cartData" ,this.cartData);
      } else {
        this.toastrService.error("Alas there seems an issue try again later");
        this.navigateRelHome();
      }
    }
    this.cartArr = cartList
    this.totalPrice =sumPrice;
    console.log("\n  this.cartData ", this.cartData);
    console.log("\n   this.totalPrice ",  this.totalPrice);
    console.log("\n    this.cartArr  ",   this.cartArr );
    this.mapLoaderActive = false;
  }

  updateCart(variantId){
    console.log("\n variantId ",variantId);
    this.router.navigate(["/user/car/" + variantId+"/update"]); 
  }

  removeFromCart(variantId){
    console.log("\n variantId ",variantId);
    console.log("\n  this.cartData ", this.cartData);
    delete this.cartData[variantId];
    console.log("\n modified cartData ",this.cartData);
    this.localStorage.set('cartData', {
      data:this.cartData
    });
    this.triggerFunctions();
  }

  bookNow(){
    console.log("\n bookNow ");
  }

}
