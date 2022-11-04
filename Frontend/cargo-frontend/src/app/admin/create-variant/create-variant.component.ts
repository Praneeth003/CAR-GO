import { Component, OnInit } from '@angular/core';
import { ConstantsModule } from 'src/app/shared/constants.module';
import { SharedService } from 'src/app/shared/shared.service';

@Component({
  selector: 'app-create-variant',
  templateUrl: './create-variant.component.html',
  styleUrls: ['./create-variant.component.css']
})
export class CreateVariantComponent implements OnInit {

  // createVariantNewParams = {imageList:[]}

  createVariantNewParams = {
    "variantName": "Sonet",
    "variantDescription": "Sonet",
    "modelId": 7,
    "colorId": 2,
    "bodyTypeId": 3,
    "fuelTypeId": 3,
    "transmissionTypeId": 3,
    "mileage": "23",
    "pricePerKilometer": "100",
    "kilometersDriven": "2567",
    "numberPlate": "AP01JS1765",
    "imageList":[]
}
  modelList = []
  bodyTypeList = []
  fuelTypeList = []
  transmissionTypeList = []
  colorList = []
  currentLocationList = []
  imageInfo = {}
  imageList = []

  validation: boolean[] = new Array(6).fill(true);
  mapLoaderActive =false;
  uploadImages=true

  constructor(private waterDataService : SharedService, private constantsModule:ConstantsModule) { }

  ngOnInit() {
    this.getModeList();
    this.getBodyTypeList();
    this.getFuelTypeList();
    this.getTransmissionTypeList();
    this.getColorList();
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

  async getLocationList() {
    this.mapLoaderActive = true;
    let result = await this.waterDataService.getLocationList().toPromise();
    console.log("\n getLocationList ",result);
    this.mapLoaderActive = false;
    if(result != null && result != undefined){
      if(result['status'] == this.constantsModule.HTTP_STATUS.SUCCESS){
        let sList =result['locationList'];
        if (sList && sList.length > 0) {
          this.currentLocationList = sList;
        }
      }
    }
  }

  appendImages(){
    this.imageInfo['imageUri'] = document.getElementById("file")['files'][0]['name']
    this.createVariantNewParams.imageList.push(this.imageInfo);
    console.log()
    this.imageInfo = {}
  }

  goToUploadImages(){
    console.log(this.createVariantNewParams)
    this.uploadImages = true;
    this.validation=new Array(6).fill(true);
  }

  createVariant(){
    console.log(this.createVariantNewParams)
  }

}
