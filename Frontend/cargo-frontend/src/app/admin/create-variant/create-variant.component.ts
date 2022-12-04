import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ConstantsModule } from 'src/app/shared/constants.module';
import { SharedService } from 'src/app/shared/shared.service';

@Component({
  selector: 'app-create-variant',
  templateUrl: './create-variant.component.html',
  styleUrls: ['./create-variant.component.css']
})
export class CreateVariantComponent implements OnInit {

  createVariantNewParams = {imageList:[]};
  uploadImages = false;

//   createVariantNewParams = {
//     "variantName": "Sonet",
//     "variantDescription": "Sonet",
//     "modelId": 7,
//     "colorId": 2,
//     "bodyTypeId": 3,
//     "fuelTypeId": 3,
//     "transmissionTypeId": 3,
//     "mileage": "23",
//     "pricePerKilometer": "100",
//     "kilometersDriven": "2567",
//     "numberPlate": "AP01JS1765",
//     "imageList":[]
// };
// uploadImages = true;
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
  currentImage;
  currentTag;


  constructor(private route: ActivatedRoute, private router: Router, private waterDataService : SharedService, private constantsModule:ConstantsModule, private toastrService: ToastrService) { }

  ngOnInit() {
    this.getModeList();
    this.getBodyTypeList();
    this.getFuelTypeList();
    this.getTransmissionTypeList();
    this.getColorList();
    this.getLocationList()
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

  signatures = {
    JVBERi0: "application/pdf",
    R0lGODdh: "image/gif",
    R0lGODlh: "image/gif",
    iVBORw0KGgo: "image/png",
    "/9j/": "image/jpg"
  };

  detectMimeType(b64) {
    for (var s in this.signatures) {
      if (b64.indexOf(s) === 0) {
        return this.signatures[s];
      }
    }
  }
  selectFile(event){
    if(!event.target.files[0] || event.target.files[0].length == 0) {
			// this.ms = 'You must select an image';
			return;
		}
		
		var mimeType = event.target.files[0].type;
		
		if (mimeType.match(/image\/*/) == null) {
			// this.msg = "Only images are supported";
			return;
		}
		
		var reader = new FileReader();
		reader.readAsDataURL(event.target.files[0]);
		
		reader.onload = (_event) => {
			this.currentImage = reader.result; 
      console.log(this.detectMimeType(this.currentImage))
		}
  }

  

  appendImages(){
    console.log("\n currentTag ",this.currentTag);
    if(!this.currentTag){
      this.toastrService.error("Please Select Image Type to continue.");
    }
    this.imageInfo['imageUri'] = this.currentImage;
    let image = {
      imageUri :this.currentImage,
      imageType : this.currentTag
    }
    this.createVariantNewParams.imageList.push(image);
    console.log(this.createVariantNewParams)
  }

  goToUploadImages(){
    console.log(this.createVariantNewParams)
    this.uploadImages = true;
    this.validation=new Array(6).fill(true);
  }

  async createVariant(){
    console.log(this.createVariantNewParams)
    let result = await this.waterDataService.post(this.createVariantNewParams,"create_variant").toPromise()
    console.log(result)
    if(result != null && result != undefined){
      if(result['status'] == this.constantsModule.HTTP_STATUS.SUCCESS){
        this.toastrService.success("Variant Created Successfully");
        // this.router.navigate(["/admin"]);
        return;
      }
    }

    this.toastrService.error("Unable to create variant.. Please contant customer care..");
  }
  

}
