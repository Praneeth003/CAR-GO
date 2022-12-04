import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { LocalStorageService } from 'angular-web-storage';
import { ConstantsModule } from 'src/app/shared/constants.module';
import { DatepickerComponent } from 'src/app/shared/datepicker/datepicker.component';
import { SharedService } from 'src/app/shared/shared.service';
import { UtilsModule } from 'src/app/shared/utils/utils/utils.module';

@Component({
  selector: 'app-journey-details',
  templateUrl: './journey-details.component.html',
  styleUrls: ['./journey-details.component.css']
})
export class JourneyDetailsComponent implements OnInit {

  @ViewChild(DatepickerComponent) datepicker: DatepickerComponent;
  
  locationList =[];
  fromModel: NgbDateStruct;
  toModel: NgbDateStruct;

  validation: boolean[] = new Array(5).fill(true);

  minDate: Date;
  maxDate: Date;
  mapLoaderActive =false;

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

  constructor(private http: HttpClient,    private waterDataService: SharedService, private constantsModule:ConstantsModule,
    private utils:UtilsModule,private router: Router,   private localStorage: LocalStorageService ){ }

  ngOnInit() {
    this.utils.clearLocalSessionStorage();
    // let data = this.localStorage.get('journeyDetailsFilter');
    // console.log("\n journeyDetailsFilter data ",data);
    // if(data != null && data != undefined && data['data'] != undefined){
    //   this.selectedFilterParams = data['data'];
    // }
    this.minDate = new Date();
    // this.maxDate = new Date();
    this.selectedFilterParams.format = "yyyyMMdd";
    this.getLocationList();
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
          this.locationList = sList;
        }
      }
    }
  }

  onFromLocationChange(){
    console.log("\n In onFromLocationChange ");
    this.validation[3] = !this.utils.isEmptyObj(this.selectedFilterParams.fromLocation);
  }

  onToLocationChange(){
    console.log("\n In onToLocationChange ");
    this.validation[4] = !this.utils.isEmptyObj(this.selectedFilterParams.toLocation);
  }

  isValid(): boolean {
    this.validation[0] = this.selectedFilterParams.sDate['value'] != null && this.selectedFilterParams.sDate['value'] != undefined;
    this.validation[1] = this.selectedFilterParams.eDate['value'] != null && this.selectedFilterParams.eDate['value'] != null;
    if (this.validation[0] && this.validation[1]) {
      this.validation[2] = this.utils.getModelDateWithDateFormat(this.selectedFilterParams.sDate['value'], this.selectedFilterParams.format) >
        this.utils.getModelDateWithDateFormat(this.selectedFilterParams.eDate['value'], this.selectedFilterParams.format) ? false : true;
    }
    this.validation[3] = !this.utils.isEmptyObj(this.selectedFilterParams.fromLocation);
    this.validation[4] = !this.utils.isEmptyObj(this.selectedFilterParams.toLocation);
    let valid = true;
    for (let ind = 0; ind < this.validation.length; ind++) {
      valid = valid && this.validation[ind];
    }
    console.log("\n In isValid ",valid);
    return valid;
  }

  requestHeatMap(){
    console.log("\n In requestHeatMap ",this.selectedFilterParams);
    if (this.isValid()) {
      console.log("\n In requestHeatMap ",this.selectedFilterParams);
      // let currentUrlParams = this.getParams();
      // currentUrlParams['mapOnClickParams'] = false;
      // currentUrlParams['component'] = this.constants.COMPONENT.RAINFALL;
      // currentUrlParams['src'] = this.selectedFilterParams.source['key'];
      // currentUrlParams['srcName'] = this.selectedFilterParams.source['value'];
      // currentUrlParams['srcUUID'] = this.selectedFilterParams.source['agencyUUID'];
      // // currentUrlParams['locname'] = this.selectedFilterParams.source['locName'];
      // // currentUrlParams['locuuid'] = this.selectedFilterParams.source['locUUID'];
      // // currentUrlParams['loctype'] = this.selectedFilterParams.source['locType'];
      // currentUrlParams['format'] = this.selectedFilterParams.format;
      this.selectedFilterParams.fsDate.value = this.utils.getModelDateWithDateFormat(this.selectedFilterParams.sDate.value, this.selectedFilterParams.format);
      this.selectedFilterParams.feDate.value = this.utils.getModelDateWithDateFormat(this.selectedFilterParams.eDate.value, this.selectedFilterParams.format);
      // currentUrlParams['aggr'] = this.selectedFilterParams.aggreType['key'];
      // currentUrlParams['view'] = this.selectedFilterParams.view;
      // // Add Min Max Date Corresponding to Source for Infobox Title 
      // let minDate = this.utils.getModelDateFromDate(this.minDate);
      // let maxDate = this.utils.getModelDateFromDate(this.maxDate);
      // currentUrlParams['maxDate'] = maxDate;
      // currentUrlParams['minDate'] = minDate;
      // this.navigate(currentUrlParams);
      this.selectedFilterParams.fromDate =this.selectedFilterParams.sDate.value;
      this.selectedFilterParams.toDate = this.selectedFilterParams.eDate.value;
      console.log("\n In requestHeatMap ",this.selectedFilterParams);
      this.constantsModule.selectedFilterParams = this.selectedFilterParams;
      console.log("\n In requestHeatMap ",this.constantsModule.selectedFilterParams);
      this.setJourneyDetails();
      this.router.navigate(["/user/home"]); 
    }
  }

  setJourneyDetails() {
    this.localStorage.set('journeyDetailsFilter', {
      data: this.selectedFilterParams
    });
  }


}
