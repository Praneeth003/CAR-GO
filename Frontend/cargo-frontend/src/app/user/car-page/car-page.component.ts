import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LocalStorageService } from 'angular-web-storage';
import { ToastrService } from 'ngx-toastr';
import { ConstantsModule } from 'src/app/shared/constants.module';
import { SharedService } from 'src/app/shared/shared.service';
import { UtilsModule } from 'src/app/shared/utils/utils/utils.module';

@Component({
  selector: 'app-car-page',
  templateUrl: './car-page.component-new.html',
  styleUrls: ['./car-page.component.css']
})
export class CarPageComponent implements OnInit {

  id = "";
  carData: any = {};
  actionType = null;
  showUpdate = false;
  disp = false;
  errorDisp = false;
  mapLoaderActive = false;
  addOnList = [];

  variantInteriorImage = {
    "variantImageId": 19,
    "variantImageData": ".//assets//Dump//interior.jpg",
    "variantImageView": "Interior",
    "variantImageDescription": "Desc",
    "variantImageStatus": true
  }

  variantImageList = [];

  selectedParams = {
    "addOnList": []
  };

  previouslySelectedAddOnID = [];

  constructor(private http: HttpClient, private httpService: SharedService, private route: ActivatedRoute, private router: Router, private localStorage: LocalStorageService,
    private waterDataService: SharedService, private constantsModule: ConstantsModule,
    private utils: UtilsModule, private toastrService: ToastrService) { }

  ngOnInit() {
    this.showUpdate = false;
    this.variantImageList = [];
    this.id = this.route.snapshot.paramMap.get('carId');
    if (this.id == null && this.id == undefined || this.id == "") {
      this.navigateRelHome();
    }
    console.log("\n CarPageComponent id", this.id);
    this.actionType = this.route.snapshot.paramMap.get('actionType');
    console.log("\n CarPageComponent actionType", this.actionType);
    if (this.actionType == 'update') {
      this.showUpdate = true;
      this.setPreviouslySelectedAddOn();
    }
    console.log("\n CarPageComponent showUpdate", this.showUpdate);
    this.getCardData();
    this.getAddOnData();
    // if(this.carData == null && this.carData == undefined){
    //   this.router.navigate(["/user/journey-details"]); 
    // }
  }

  setPreviouslySelectedAddOn(){
    let data = this.localStorage.get('cartData');
    console.log("\n existing cartData ", data);
    if (data != null && data != undefined && data['data'] != undefined) {
      let cartData = data['data'];
      console.log("\n cartData", cartData);
      let currentCartData = cartData[this.id];
      console.log("\n currentCartData ", currentCartData);
      this.previouslySelectedAddOnID = currentCartData['postData']['addOnIds'];
      console.log("\n previouslySelectedAddOnID ", this.previouslySelectedAddOnID);
    } else {
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

  async getCardData() {
    this.mapLoaderActive = true;
    this.disp = false;
    this.errorDisp = false;
    let endpoint = "variant_by_id/" + this.id;
    this.variantImageList = [];
    let result = await this.httpService.get(endpoint).toPromise();
    if (result != null && result != undefined) {
      let sList = result['variantList'];
      if (sList && sList.length > 0) {
        this.carData = sList[0];
        // this.variantImageList.push(this.carData['variantImage']);
        this.variantImageList = this.carData['variantImageList'];
        this.variantImageList.push(this.variantInteriorImage);
        this.disp = true;
        console.log("\n this.carData  ", this.carData, " this.variantImageList ", this.variantImageList);
      } else {
        this.errorDisp = true;
      }
    } else {
      this.errorDisp = true;
    }
    this.mapLoaderActive = false;
  }



  async getAddOnData() {
    this.mapLoaderActive = true;
    this.disp = false;
    this.errorDisp = false;
    let endpoint = "add_on";
    let result = await this.httpService.get(endpoint).toPromise();
    if (result != null && result != undefined) {
      let sList = result['addOnList'];
      if (sList && sList.length > 0) {
        for (let item of sList) {
          let index = -1;
          index = this.previouslySelectedAddOnID.findIndex(src => src == item['addOnId']);
          if (index !== -1) {
            item['isVisibile'] = true;
            this.selectedParams.addOnList.push(item);
          }else{
            item['isVisibile'] = false;
          }
        
          this.addOnList.push(item);
        }
        console.log("\n this.addOnList  ", this.addOnList);
        console.log("\n  this.selectedParams ", this.selectedParams);
      } else {
        this.errorDisp = true;
      }
    } else {
      this.errorDisp = true;
    }
    this.mapLoaderActive = false;
  }

  addToCart() {
    // this.router.navigate(["/user/cart"]); 
    this.checkAndAddToCartSession();
  }

  updateCart() {
    // this.router.navigate(["/user/cart"]); 
    this.checkAndAddToCartSession();
  }

  bookNow() {
    this.setSelectedCart();
    this.checkAddToCart();
  }

  checkAndAddToCartSession() {
    let selectedCartData = null, journeyDetailsFilterData = null, userData = null;
    let data = this.localStorage.get('userDetails');
    console.log("\n journeyDetailsFilter data ", data);
    if (data != null && data != undefined && data['data'] != undefined) {
      userData = data['data'];
    }


    let addOnId = [];
    for (let addOn of this.selectedParams.addOnList) {
      addOnId.push(addOn['addOnId']);
    }
    selectedCartData = {
      variant: this.carData,
      addOnList: addOnId
    };
    console.log("\n selectedcart data ", data);
    // if(data != null && data != undefined && data['data'] != undefined){
    //   selectedCartData = data['data'];
    // }

    data = this.localStorage.get('journeyDetailsFilter');
    console.log("\n journeyDetailsFilter data ", data);
    if (data != null && data != undefined && data['data'] != undefined) {
      journeyDetailsFilterData = data['data'];
    }

    if (journeyDetailsFilterData == null || journeyDetailsFilterData == undefined || selectedCartData == null || selectedCartData == undefined) {
      this.router.navigate(["/user/journey-details"]);
    } else {
      // this.addToCartAPI(selectedCartData,journeyDetailsFilterData,userData);
      this.mapLoaderActive = true;
      let userId = null;
      if (userData != null && userData != undefined) {
        userId = userData['userId']
      }
      let reqData = {
        "userId": userId,
        "variantId": selectedCartData['variant']['variantId'],
        "fromDate": journeyDetailsFilterData["fromDate"],
        "toDate": journeyDetailsFilterData["toDate"],
        "pickupLocationId": journeyDetailsFilterData["fromLocation"]["locationId"],
        "dropLocationId": journeyDetailsFilterData["toLocation"]["locationId"],
        "addOnIds": selectedCartData['addOnList']
      };
      console.log("\n addToCartAPI reqData ", reqData);

      let data = this.localStorage.get('cartData');
      console.log("\n existing cartData ", data);
      let exsitingCartData = {};
      if (data != null && data != undefined && data['data'] != undefined) {
        exsitingCartData = data['data'];
      }
      exsitingCartData[reqData['variantId']] = {
        variant: this.carData,
        addOnList: this.addOnList,
        postData: reqData
      };
      console.log("\n modified cartData ", exsitingCartData);
      this.localStorage.set('cartData', {
        data: exsitingCartData
      });
      this.router.navigate(["/user/cart"]);
    }
  }

  // setAddToCart() {
  //   let data = this.localStorage.get('cartData');
  //   console.log("\n journeyDetailsFilter data ",data);
  //   let exsitingCartData={};
  //   if(data != null && data != undefined && data['data'] != undefined){
  //     exsitingCartData= data['data'];
  //   }
  //   exsitingCartData[]

  //   this.localStorage.set('cartData', {
  //     data:{
  //       variant:this.carData,
  //       addOnList:this.addOnList
  //     }
  //   });
  // }

  checkAddToCart() {
    let selectedCartData = null, journeyDetailsFilterData = null, userData = null;
    let data = this.localStorage.get('userDetails');
    console.log("\n journeyDetailsFilter data ", data);
    if (data != null && data != undefined && data['data'] != undefined) {
      userData = data['data'];
    }


    let addOnId = [];
    for (let addOn of this.selectedParams.addOnList) {
      addOnId.push(addOn['addOnId']);
    }
    selectedCartData = {
      variant: this.carData,
      addOnList: addOnId
    };
    console.log("\n selectedcart data ", data);
    // if(data != null && data != undefined && data['data'] != undefined){
    //   selectedCartData = data['data'];
    // }

    data = this.localStorage.get('journeyDetailsFilter');
    console.log("\n journeyDetailsFilter data ", data);
    if (data != null && data != undefined && data['data'] != undefined) {
      journeyDetailsFilterData = data['data'];
    }

    if (journeyDetailsFilterData == null || journeyDetailsFilterData == undefined || selectedCartData == null || selectedCartData == undefined) {
      this.router.navigate(["/user/journey-details"]);
    } else {
      this.addToCartAPI(selectedCartData, journeyDetailsFilterData, userData);
    }

  }

  async addToCartAPI(selectedCartData, journeyDetailsFilterData, userData) {
    let endPoint = 'add_to_cart';
    let cartResponse = null;
    this.mapLoaderActive = true;
    let userId = null;
    if (userData != null && userData != undefined) {
      userId = userData['userId']
    }
    let reqData = {
      "userId": userId,
      "variantId": selectedCartData['variant']['variantId'],
      "fromDate": journeyDetailsFilterData["fromDate"],
      "toDate": journeyDetailsFilterData["toDate"],
      "pickupLocationId": journeyDetailsFilterData["fromLocation"]["locationId"],
      "dropLocationId": journeyDetailsFilterData["toLocation"]["locationId"],
      "addOnIds": selectedCartData['addOnList']
    };
    console.log("\n addToCartAPI reqData ", reqData);
    let result = await this.waterDataService.post(reqData, endPoint).toPromise();
    console.log("\n addToCartAPI result ", result);
    this.mapLoaderActive = false;
    if (result != null && result != undefined) {
      if (result['status'] == this.constantsModule.HTTP_STATUS.SUCCESS && result['cartEntry'] != undefined && result['cartEntry']['cartId'] != undefined) {
        cartResponse = result['cartEntry'];
        this.setCartResponse(cartResponse);
        this.setCartResponse1(reqData['variantId'],cartResponse);
        console.log("\n addToCart this.cartResponse \n", cartResponse);
        this.router.navigate(["/user/payment"]);
        this.toastrService.success("You have successfully added Cart Information", "");
      } else {
        this.toastrService.error("Alas there seems an issue try again later" + result.errorDescription, "");
        // if(selectedCartData['variant'] && selectedCartData['variant']['variantId']){
        //   this.router.navigate(["/user/car/" + selectedCartData['variant']['variantId']]); 
        // }else{
        //   this.router.navigate(["/user/journey-details"]); 
        // }
      }
    }
    this.mapLoaderActive = false;
  }

  onAddOnChange(event, item) {
    console.log("\n onBodyTypeChange item", item, "event", event, " selectedFilterParams ", this.selectedParams);
    if (event.target.checked) {
      this.selectedParams.addOnList.push(item);
    } else {
      let index = -1;
      index = this.selectedParams.addOnList.findIndex(src => src['addOnId'] == item['addOnId']);
      if (index !== -1) {
        this.selectedParams.addOnList.splice(index, 1);
      }
    }
    console.log("\n selectedParams ", this.selectedParams);
  }

  setSelectedCart() {
    this.localStorage.set('selectedCart', {
      data: {
        variant: this.carData,
        addOnList: this.addOnList
      }
    });
  }

  setCartResponse1(variantId,cartResponse) {
    let data = this.localStorage.get('cartResponse');
    console.log("\n existing cartResponse ", data);
    let existingCartResponseData = {};
    if (data != null && data != undefined && data['data'] != undefined) {
      existingCartResponseData = data['data'];
    }
    existingCartResponseData[variantId] = cartResponse;
    console.log("\n modified existingCartResponseData ", existingCartResponseData);
    this.localStorage.set('cartResponse', {
      data: existingCartResponseData
    });
  }

  setCartResponse(cartResponse) {
    this.localStorage.set('selectedCartResponse', {
      data: cartResponse
    });
  }

  setActiveClass(item) {
    console.log("\n setActiveClass ", item);
    let index = this.addOnList.findIndex(src => src['addOnId'] == item['addOnId']);
    if (index != -1) {
      this.addOnList[index]['isVisible'] = !this.addOnList[index]['isVisible'];
    }
  }

  setActiveAddOn(item) {
    console.log("\n setActiveAddOn ", item);
    let index = this.addOnList.findIndex(src => src['addOnId'] == item['addOnId']);
    if (index != -1) {
      this.addOnList[index]['isVisibile'] = !this.addOnList[index]['isVisibile'];
    }
  }

}
