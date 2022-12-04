import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from 'angular-web-storage';
import { ConstantsModule } from 'src/app/shared/constants.module';
import { SharedService } from 'src/app/shared/shared.service';
import { UtilsModule } from 'src/app/shared/utils/utils/utils.module';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page-new.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  selectedFilterParams = {
    modelList:[],
    bodyTypeList:[],
    fuelTypeList:[],
    transmissionTypeList:[],
    colorList:[]
  };
  
  journeyDetailsFilter ={};

  mapLoaderActive =false;

  modelList=[];
  bodyTypeList=[];
  fuelTypeList=[];
  transmissionTypeList=[];
  colorList=[];
  variantList =[];
  varaint1 ={};
  disp= true;
  errorDisp = false;

  constructor(private http: HttpClient,    private waterDataService: SharedService, private constantsModule:ConstantsModule,
    private utils:UtilsModule,private router: Router,   private localStorage: LocalStorageService ){ }

  ngOnInit() {
    this.localStorage.remove("selectedVariant");
    this.varaint1 = ".//assets//Dump//Volkswagen//Polo//white.jpg";
    this.disp= false;
    console.log("\n HomePageComponent selectedFilterParams",this.constantsModule.selectedFilterParams);
    // this.selectedFilterParams = this.constantsModule.selectedFilterParams;
    let data = this.localStorage.get('journeyDetailsFilter');
    console.log("\n journeyDetailsFilter data ",data);
    if(data != null && data != undefined && data['data'] != undefined){
      this.journeyDetailsFilter = data['data'];
    }else{
      this.router.navigate(["/user/journey-details"]); 
    }

    this.getVariants();
    this.getModeList();
    this.getBodyTypeList();
    this.getFuelTypeList();
    this.getTransmissionTypeList();
    this.getColorList();
  }

  async getVariants(){
    this.disp = false;
    this.errorDisp = false;
    this.variantList = [];
    console.log("\n getVariants this.selectedFilterParams ",this.selectedFilterParams,
    " this.journeyDetailsFilter ",this.journeyDetailsFilter);
    // let copySelectedFIlterParams = this.utils.deepClone(this.selectedFilterParams);
    // let copyJourneyDetails = this.utils.deepClone(this.journeyDetailsFilter);
    let endPoint = 'variant';
    let params = this.utils.deepMerge(this.selectedFilterParams,this.journeyDetailsFilter);
    console.log("\n getVariants params ",params);
    this.mapLoaderActive = true;
    let result = await this.waterDataService.post(params,endPoint).toPromise();
    console.log("\n getVariants result ",result);
    this.mapLoaderActive = false;
    if(result != null && result != undefined){
      if(result['status'] == this.constantsModule.HTTP_STATUS.SUCCESS){
        let sList =result['variantList'];
        if (sList && sList.length > 0) {
          this.setVariantList(sList);
        }else{
          this.errorDisp = true;
        }
      }else{
        this.errorDisp = true;
      }
    }else{
        this.errorDisp = true;
    }
  }

  setVariantList(sList){
    console.log("\n setVariantList sList ",sList);
    this.variantList = sList;
    this.disp = true;
    console.log("\n setVariantList variantList ",this.variantList);
  }

  async getModeList(){
    let endPoint = 'model';

    this.mapLoaderActive = true;
    let result = await this.waterDataService.post({},endPoint).toPromise();
    console.log("\n getModeList ",result);
    this.mapLoaderActive = false;
    if(result != null && result != undefined){
      if(result['status'] == this.constantsModule.HTTP_STATUS.SUCCESS){
        let sList =result['modelList'];
        if (sList && sList.length > 0) {
          this.modelList = sList;
        }
      }
    }

  }

  onModelChange(event,item){
    console.log("\n onModelChange item",item,"event",event," selectedFilterParams ",this.selectedFilterParams);
    if (event.target.checked){
      this.selectedFilterParams.modelList.push(item);
    }else{
      let index =-1;
      index = this.selectedFilterParams.modelList.findIndex(src => src['modelId'] == item['modelId']);
      if (index !== -1) {
        this.selectedFilterParams.modelList.splice(index, 1);
      }   
    }
    console.log("\n selectedFilterParams ",this.selectedFilterParams);
    this.getVariants();
  }

  async getBodyTypeList(){
    let endPoint = 'bodyType';

    this.mapLoaderActive = true;
    let result = await this.waterDataService.post({},endPoint).toPromise();
    console.log("\n getBodyTypeList ",result);
    this.mapLoaderActive = false;
    if(result != null && result != undefined){
      if(result['status'] == this.constantsModule.HTTP_STATUS.SUCCESS){
        let sList =result['bodyTypeList'];
        if (sList && sList.length > 0) {
          this.bodyTypeList = sList;
        }
      }
    }

  }

  onBodyTypeChange(event,item){
    console.log("\n onBodyTypeChange item",item,"event",event," selectedFilterParams ",this.selectedFilterParams);
    if (event.target.checked){
      this.selectedFilterParams.bodyTypeList.push(item);
    }else{
      let index =-1;
      index = this.selectedFilterParams.bodyTypeList.findIndex(src => src['bodyTypeId'] == item['bodyTypeId']);
      if (index !== -1) {
        this.selectedFilterParams.bodyTypeList.splice(index, 1);
      }   
    }
    console.log("\n selectedFilterParams ",this.selectedFilterParams);
    this.getVariants();
  }

  async getFuelTypeList(){
    let endPoint = 'fuelType';

    this.mapLoaderActive = true;
    let result = await this.waterDataService.post({},endPoint).toPromise();
    console.log("\n getFuelTypeList ",result);
    this.mapLoaderActive = false;
    if(result != null && result != undefined){
      if(result['status'] == this.constantsModule.HTTP_STATUS.SUCCESS){
        let sList =result['fuelTypeList'];
        if (sList && sList.length > 0) {
          this.fuelTypeList = sList;
        }
      }
    }
  }

  onFuelTypeChange(event,item){
    console.log("\n onFuelTypeChange item",item,"event",event," selectedFilterParams ",this.selectedFilterParams);
    if (event.target.checked){
      this.selectedFilterParams.fuelTypeList.push(item);
    }else{
      let index =-1;
      index = this.selectedFilterParams.fuelTypeList.findIndex(src => src['fuelTypeId'] == item['fuelTypeId']);
      if (index !== -1) {
        this.selectedFilterParams.fuelTypeList.splice(index, 1);
      }   
    }
    console.log("\n selectedFilterParams ",this.selectedFilterParams);
    this.getVariants();
  }

  async getTransmissionTypeList(){
    let endPoint = 'transmissionType';

    this.mapLoaderActive = true;
    let result = await this.waterDataService.post({},endPoint).toPromise();
    console.log("\n getTransmissionTypeList ",result);
    this.mapLoaderActive = false;
    if(result != null && result != undefined){
      if(result['status'] == this.constantsModule.HTTP_STATUS.SUCCESS){
        let sList =result['transmissionTypeList'];
        if (sList && sList.length > 0) {
          this.transmissionTypeList = sList;
        }
      }
    }
  }

  onTransmissionTypeChange(event,item){
    console.log("\n onTransmissionTypeChange item",item,"event",event," selectedFilterParams ",this.selectedFilterParams);
    if (event.target.checked){
      this.selectedFilterParams.transmissionTypeList.push(item);
    }else{
      let index =-1;
      index = this.selectedFilterParams.transmissionTypeList.findIndex(src => src['transmissionTypeId'] == item['transmissionTypeId']);
      if (index !== -1) {
        this.selectedFilterParams.transmissionTypeList.splice(index, 1);
      }   
    }
    console.log("\n selectedFilterParams ",this.selectedFilterParams);
    this.getVariants();
  }

  async getColorList(){
    let endPoint = 'color';

    this.mapLoaderActive = true;
    let result = await this.waterDataService.post({},endPoint).toPromise();
    console.log("\n getColorList ",result);
    this.mapLoaderActive = false;
    if(result != null && result != undefined){
      if(result['status'] == this.constantsModule.HTTP_STATUS.SUCCESS){
        let sList =result['colorList'];
        if (sList && sList.length > 0) {
          this.colorList = sList;
        }
      }
    }
  }

  onColorChange(event,item){
    console.log("\n onColorChange item",item,"event",event," selectedFilterParams ",this.selectedFilterParams);
    if (event.target.checked){
      this.selectedFilterParams.colorList.push(item);
    }else{
      let index =-1;
      index = this.selectedFilterParams.colorList.findIndex(src => src['colorId'] == item['colorId']);
      if (index !== -1) {
        this.selectedFilterParams.colorList.splice(index, 1);
      }   
    }
    console.log("\n selectedFilterParams ",this.selectedFilterParams);
    this.getVariants();
  }

  expandCar(item){
    this.setSelectedVariant(item);
    this.router.navigate(["/user/car/" + item.variantId+"/add"]); 
  }


  setSelectedVariant(variant) {
    this.localStorage.set('selectedVariant', {
      data:variant
    });
  }

}
