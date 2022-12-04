import { timestamp } from 'rxjs/operators';
import { element } from 'protractor';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { namespaceHTML } from '@angular/core/src/render3';
import { ConstantsModule } from '../../constants.module';
import { isNull, isNullOrUndefined } from 'util';
import { LocalStorageService } from 'angular-web-storage';

@NgModule({
 declarations: [],
 imports: [
 CommonModule
 ]
})
export class UtilsModule {

  pattern =/^([0-9]{2})\/([0-9]{2})\/([0-9]{4})$/;
  monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
  ];
  dayNames = ["Sun","Mon","Tue","Wed","Thu","Fri","Sat"];
  showSidemenu: boolean=false;

  defaultLocationObj = {
    locationName: this.constants.KERALA,
    locationUUID: this.constants.KERALA_UUID,
    locationType:this.constants.LOCATION_CONSTANT.STATE
  }

  constructor(private constants: ConstantsModule, private router: Router,private localStorage: LocalStorageService){}


  // To be used for Preparation of Common PostParams for both MIS and GIS
  getCommonParamsForMISAndGIS(){
    let mapping={
      component:'component',
      src:'src',
      srcUUID:'srcUUID',
      srcName:'srcName',
      pType:'pType',
      loctype:'pType',
      cType:'cType',
      seasonType:'seasonType',
      sDate:'sDate',
      eDate:'eDate',
      format:'format',
      seasonYear:'seasonYear',
      eYear:'seasonYear',
      locuuid:'locUUID',
      locUUID:'locUUID',
      view:'view',
      cumEndYear :'seasonYear'
      }
      return mapping;
  }

  changeParamsToCommonParams(urlParams={}){
    let modifiedParams = {}, commonParams = this.getCommonParamsForMISAndGIS();
    for(let key in urlParams){
      if(commonParams[key]){
        modifiedParams[commonParams[key]] = urlParams[key];
      }else{
        modifiedParams[key] = urlParams[key];
      }
    }
    return modifiedParams;
  }

  getPostDataForLocationTypeList(currentUrlParams){
    currentUrlParams = this.changeParamsToCommonParams(currentUrlParams);
    let postData ={
      component:currentUrlParams['component'],
      srcUUID:currentUrlParams['srcUUID'] || "4a2919f0-8857-11ea-ad17-000d3a320689",
      pEUUID:this.constants.KERALA_UUID,
      view: currentUrlParams['view']+"_HIERARCHY"
    }
    return postData;
  }

  getIndexAndDistrictOrder(labels =[]){
    let districtOrder = this.constants.DISTRICT_ORDER_ACTUAL_NAME;
    let indexOrder =[],labelOrder =[];
    for(let index=0;index<districtOrder.length;index++){
      let ind = labels.findIndex(dist=>dist.toLowerCase()== districtOrder[index].toLowerCase());
      if(ind != -1){
        labelOrder.push(districtOrder[index]);
        indexOrder.push(ind);
      }
    }
    return {labels:labelOrder,index:indexOrder};
  }
 
  getseasonlist(component: any):object[]{
    switch(component){
      case this.constants.COMPONENT.GROUNDWATER:
        return [{"key":"season1","value":"Jan- Mar","show":false,"months":"01-03"},{"key":"season2","value":"Apr - Jun","show":false,"months":"04-06"},{"key":"season3","value":"Jul - Sep","show":false,"months":"07-09"},{"key":"season4","value":"Oct - Dec","show":false,"months":"10-12"}]
      case this.constants.COMPONENT.SWATERQUALITY:
        return [{"key":"season1","value":"Jun-Sep","show":false,"months":"06-09"},{"key":"season2","value":"Oct-Jan","show":false,"months":"10-01"},{"key":"season3","value":"Feb-May","show":false,"months":"02-05"}]
      case this.constants.COMPONENT.LI_SCHEMES:
        return [{"key":"season1","value":"Summer(Jan - Apr)","show":false,"months":"01-04"},{"key":"season2","value":"Autumn(May - Sept)","show":false,"months":"05-09"},{"key":"season3","value":"Winter(Oct - Dec)","show":false,"months":"10-12"},{"key":"annual","value":"Annual(Jan - Dec)","show":false,"months":"01-12"}]
      case this.constants.COMPONENT.WATER_DEMAND:
        return [{"key":"season1","value":"Apr- Oct","show":false,"months":"04-10"}, {"key":"season2","value":"Sep - Jan","show":false,"months":"09-01"},{"key":"season3","value":"Dec - Apr","show":false,"months":"12-04"},{"key":"annual","value":"Jan- Dec","show":false,"months":"01-12"}]
      case this.constants.COMPONENT.CROP_DEMAND:
          return [{"key":"season1","value":"Apr- Oct(Virippu)","show":false,"months":"04-10"}, {"key":"season2","value":"Sep - Jan(Mundakan)","show":false,"months":"09-01"},{"key":"season3","value":"Dec - Apr(Puncha)","show":false,"months":"12-04"},{"key":"annual","value":"Apr- Apr(Annual)","show":false,"months":"01-12"}]
      case this.constants.COMPONENT.PANCHYATH_LEVEL_WATER_BUDGET:
            return [{"key":"1","value":"Apr- Oct(Virippu)","show":false,"months":"04-10"}, 
            {"key":"2","value":"Sep - Jan(Mundakan)","show":false,"months":"09-01"},
            {"key":"3","value":"Dec - Apr(Puncha)","show":false,"months":"12-04"},
            {"key":"4","value":"Apr- Apr(Annual)","show":false,"months":"01-12"}]
      case this.constants.COMPONENT.BASIN_LEVEL_WATER_BUDGET:
            return [{"key":"1","value":"Apr- Oct(Virippu)","show":false,"months":"04-10"}, 
            {"key":"2","value":"Sep - Jan(Mundakan)","show":false,"months":"09-01"},
            {"key":"3","value":"Dec - Apr(Puncha)","show":false,"months":"12-04"},
            {"key":"4","value":"Apr- Apr(Annual)","show":false,"months":"01-12"}]
      default:
        return [{"key":"season1","value":"Jan- Mar","show":false,"months":"01-03"},{"key":"season2","value":"Apr - Jun","show":false,"months":"04-06"},{"key":"season3","value":"Jul - Sep","show":false,"months":"07-09"},{"key":"season4","value":"Oct - Dec","show":false,"months":"10-12"}]
    }
  }

  getseasontitle(seasontype,year, component){
    let seasonlist = this.getseasonlist(component);
    let index  = seasonlist.findIndex(data => data['key'] == seasontype)
    return seasonlist[index]['value'] +" "+year;
  }

  getSeasonType(year: number, component: any, currDate: Date = new Date()): string {
    let currYear = currDate.getFullYear();
    let month = currDate.getMonth();
    let type: any;

    switch (component) {
      case this.constants.COMPONENT.GROUNDWATER:
        type = "4";
        if (year && year == currYear)
          type = (Math.floor(month / 3) + 1).toString();
        break;

      case this.constants.COMPONENT.LI_SCHEMES:
        type = "4";
        if (year && year == currYear) {
          if (month < 5) {
            type = '1';
          } else if (month < 10) {
            type = '2';
          }
        }
        break;

      case this.constants.COMPONENT.SWATERQUALITY:
        if (year == currYear) {
          if (month >= 5 && month <= 8)
            type = "1";
          else if (month >= 9 && month <= 11)
            type = "2";
          else
            type = "-1";
        }
        else if (year == currYear - 1 && month == 0)
          type = "2";

        else type = "3";
        break;

      case this.constants.COMPONENT.CROP_DEMAND:
        type = "4";
        break;
      
      case this.constants.COMPONENT.PANCHYATH_LEVEL_WATER_BUDGET:
          type = "4";
          break;

      default:
        type = '4';
        break;
    }
    return type;
  }

  toggleSidemenu(){
    this.showSidemenu=!this.showSidemenu;
  }

  navigateToHome(){
    if(!this.constants.IS_EXTERNAL_SYSTEM){
      if(!this.constants.IS_COMPONENT_SPECIFIC_USER){
        let  urlLoc = String(location), baseHref='/';
        if(urlLoc.includes('staging')){
          baseHref = '/staging';
        }
        this.router.navigateByUrl(baseHref);
      }
    }

  }

  getSidemenu(){
    return this.showSidemenu;
  }

  getWritableObject(obj: object): object{
    if(obj == null || obj == undefined) return obj;

    let objectConfig = {};
    for(let key in obj){
      objectConfig[key] = obj[key];
    }

    return objectConfig;
  }

  checkDateRange(sDate: Date, eDate: Date, minDate:Date, maxDate: Date)
  {
    // if(sDate!=undefined&&sDate<minDate)
    // sDate=minDate;
    // else if(sDate!=undefined&&sDate>maxDate)
    // sDate=maxDate;
    
    // if(eDate!=undefined&&eDate<minDate)
    // eDate=minDate;
    // else if(eDate!=undefined&&eDate>maxDate)
    // eDate=maxDate;

    if(sDate!=undefined&&sDate<minDate)
    sDate=minDate;
    else if(sDate!=undefined&&sDate>maxDate)
    sDate=minDate;
    
    if(eDate!=undefined&&eDate<minDate)
    eDate=maxDate;
    else if(eDate!=undefined&&eDate>maxDate)
    eDate=maxDate;

    return {"eDate": eDate, "sDate": sDate};
  }

  isEmptyObj(obj) {
    for (var prop in obj) {
      if (obj.hasOwnProperty(prop)) {
        return false;
      }
    }
    return JSON.stringify(obj) === JSON.stringify({});
  }

  
  capitalize(word: string): string{
    if(!word) return '';
    word = word.toLowerCase();
    return word.replace(/^\w/, c => c.toUpperCase());
  }

  pascalCase(str:string):string {
    if(!str) return '';
    let res = str.split(' ');
    let res1 ='';
    for (let  i = 0; i < res.length; i++) {
      if(i == 0)
        res1 += this.capitalize(res[i]);
      else
        res1 += (" " + this.capitalize(res[i]));
    }
   return res1;
  }

//   getComponentName(word: string): string {
//     let components = {rainfall:'Rainfall',
//     reservoir:'Reservoir',
//     RiverAuthority:'River Point Authority',
//     groundwater:'Ground Water',
//     et:'Evapotranspiration',
//     soilmoisture: 'Soil Moisture',
//     surfacewater_wq : 'Surface Water Quality',
//     groundwater_wq : 'Ground Water Quality',
// [this.constants.COMPONENT.MI_TANKS]:'Minor Irrigation Tanks'
//   }
  //   if(components[word]) return components[word];
  //   return word;
  // }

  // isValidDataQuery(options: object): boolean{
  //   if(!options || !options['parentLocName'] || !options['component'] || !this.constants.COMPONENT_FORM_FIELDS[options['component']]) return false;
  //   let valid = true;
  //   for(let index=0; index < this.constants.COMPONENT_FORM_FIELDS[options['component']].length; index++){
  //     let field = this.constants.COMPONENT_FORM_FIELDS[options['component']][index];
  //     if(Object.keys(options).indexOf(field) == -1){
  //       valid = false;
  //       break;
  //     }
      
  //   }
  //   return valid;
  // }

  getTableSummaryIndex(tableData: any): number{
    if(!tableData)  return -1;
    let position = -1;

    let index = tableData.findIndex(item => item['name'].toUpperCase() === "TOTAL");
    if(index>= 0){
      position = index;
    }
    return position;
  }

  getDateObjectFromModelDate(date: string): Date{
    if(!date) return undefined;
    let year = parseInt(date.substr(0, 4), 10);
    let month = parseInt(date.substr(4,2), 10);
    let day = parseInt(date.substr(6,2), 10);

    return new Date(year, (isNaN(month)?0:(month-1)), (isNaN(day)?1:day));
  }

  getDayFromDate(date: string){
   let str : string;
    str = this.getDateInFormatFromModelDate(date,'mm-dd-yyyy');
    var mydate = new Date(str);
 //   let day = this.dayNames[date.getDay()];
     let day=mydate.toString();
    day = day.slice(0,3);
    return day;
    }

    getDateInFormatFromTimeStamp(timestamp)
    {
    var date = new Date(timestamp);
    return date.toLocaleTimeString(navigator.language, {hour: '2-digit', minute:'2-digit'});
    }
    
    getAddedDateFromTimeStamp(timestamp,hour)
    {
    let date =new Date(timestamp + hour*3600*1000 );
    return this.getDateInFormatFromTimeStamp(date);
    }
    
  getTodayAsModelDate(): string{
    let date = new Date();
    return this.getModelDateFromDate(date);
  }

  getModelDateFromDate(date: Date): string{
    if(date){
      let year = (date.getFullYear()).toString();
      let month = (((date.getMonth()+1)<10)?"0"+(date.getMonth()+1):date.getMonth()+1).toString();
      let day = ((date.getDate() < 10)?"0"+date.getDate():date.getDate()).toString();
      return year+month+day;
    } else return null;
  }

  getModelDateWithDateFormat(date: Date, format: string): string{
    if(!date) return '';
    
    let result = '';
    if(format == this.constants.DATEPICKER_DATE_FORMAT.YEAR)
      result = this.getModelDateFromDate(date).substr(0,4);
    else if(format == this.constants.DATEPICKER_DATE_FORMAT.MONTH)
      result = this.getModelDateFromDate(date).substr(0,6);
    else
      result = this.getModelDateFromDate(date);
    
    return result;
  }

  deepClone(object): any {
    if (Array.isArray(object)) {
      return JSON.parse(JSON.stringify(object));
    }
    const cloneObj = ( object.constructor() as any);
    const attributes = Object.keys(object);
    for (const attribute of attributes) {
      const property = object[attribute];

      if (typeof property === 'object') {
        cloneObj[attribute] = this.deepClone(property);
      } else {
        cloneObj[attribute] = property;
      }
    }
    return cloneObj;
  }

  deepMerge(src: any, dest: any, options?: any): any {
    const result = {};
    // tslint:disable-next-line: forin
    for (const i in src) {
      const type = typeof src[i];
      if (!isNullOrUndefined(options) && !isNullOrUndefined(options.customMerge) && !isNullOrUndefined(options.customMerge[i])) {

        // tslint:disable-next-line: triple-equals
        if (options.customMerge[i] == 'dest') {
          result[i] = !isNullOrUndefined(dest[i]) ? dest[i] : null;
          // tslint:disable-next-line: triple-equals
        } else if (options.customMerge[i] == 'src') {
          result[i] = !isNullOrUndefined(src[i]) ? src[i] : null;
        } else {
          result[i] = options.customMerge[i](src[i], dest[i]);
        }
      } else if (!isNullOrUndefined(src) && !isNull(src[i]) && type == 'object' && !(src[i] instanceof Array)) {
        result[i] = (!isNullOrUndefined(dest) && !isNullOrUndefined(dest[i])) ? this.deepMerge(src[i], dest[i], options) : src[i];
      } else if (!isNullOrUndefined(src) && !isNullOrUndefined(src[i]) && src[i] instanceof Array) {
        if (!isNullOrUndefined(options) && !isNullOrUndefined(options.array) && options.array == 'merge') {
          result[i] = (!isNullOrUndefined(dest) && !isNullOrUndefined(dest[i]) && dest[i].length > 0) ? src[i].concat(dest[i]) : src[i];
        } else if (!isNullOrUndefined(options) && !isNullOrUndefined(options.array) && options.array == 'override') {
          result[i] = (!isNullOrUndefined(dest) && !isNullOrUndefined(dest[i])) ? dest[i] : src[i];
        } else {
          result[i] = (!isNullOrUndefined(dest) && !isNullOrUndefined(dest[i]) && dest[i].length > 0) ? src[i].concat(dest[i]) : src[i];
        }
      } else {
        result[i] = (!isNullOrUndefined(dest) && !isNullOrUndefined(dest[i])) ? dest[i] : src[i];
      }
    }
    for (const i in dest) {
      if (isNullOrUndefined(result[i])) {
        result[i] = dest[i];
      }
    }
    return result;
  }


  replaceStringAt(original: string, index: number, replacement: string): string{
    return original.substr(0, index) + replacement+ original.substr(index + replacement.length);
  }

  validDateFormat(format: string): boolean{
    return (format && format.toLowerCase().indexOf("season") < 0);
  }

  validDateFormatGw(format: string): boolean{
    return (format && format.toString().indexOf("-") < 0);
  }

  /**
   * Logic :
   * prepare the values in all possible formats i.e MMM, mm dd etc
   * use these values to replace the format keys in the original format string
   * @param date 
   * @param format 
   */
  getDateInFormatFromModelDate(date: string, format: string, maxDate?: string): string{
    if(!date) return date;


    if(maxDate && maxDate < date){
      let year = parseInt(maxDate.substr(0, 4), 10);
      let month = parseInt(maxDate.substr(4,2), 10);
      let day = parseInt(maxDate.substr(6,2), 10);

      try{
        return this.getDateInFormat(year, month, day, format);
      }catch(e){
        throw e;
      }
    } else {
      let year = parseInt(date.substr(0, 4), 10);
      let month = parseInt(date.substr(4,2), 10);
      let day = parseInt(date.substr(6,2), 10);

      try{
        return this.getDateInFormat(year, month, day, format);
      }catch(e){
        throw e;
      }
    }
  }

  getDateInDefaultFormatFromModelDate(date: string){
    if(date == undefined || date.length < 4 || date.length > 8) return;
    else if(date.length == 4) return this.getDateInFormatFromModelDate(date, "YYYY")
    else if(date.length == 6) return this.getDateInFormatFromModelDate(date, "MMM-YYYY")
    else if(date.length == 8) return this.getDateInFormatFromModelDate(date, "DD-MMM-YYYY")
  }

  getDateInFormat(year: number, month: number, day: number, format: string): string{
    if(!this.validDateFormat(format))
      throw "Invalid date format";
    
    let monthFormat = {
      MMM : ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
    }
    let result = format;
    /**
     * this object will be used to replace the values in the key in the required format
     */
    let formattedDateObject = {
      MMM : (format.indexOf('MMM') >= 0)?monthFormat['MMM'][month-1]:'',
      mmm : (format.indexOf('mmm') >= 0)?monthFormat['MMM'][month-1]:'',
      MM : (format.indexOf('MM') >= 0)?(month < 10)?("0"+month):month:'',
      mm : (format.indexOf('mm') >= 0)?(month < 10)?("0"+month):month:'',
      yyyy : (format.indexOf('yyyy') >= 0)?year:'',
      YYYY : (format.indexOf('YYYY') >= 0)?year:'',
      yy : (format.indexOf('yy') >= 0)?year.toString().substr(2,2):'',
      YY : (format.indexOf('YY') >= 0)?year.toString().substr(2,2):'',
      dd : (format.indexOf('dd') >= 0)?(day < 10)?("0"+day):day:'',
      DD : (format.indexOf('DD') >= 0)?(day < 10)?("0"+day):day:''
    }

    for(let key in formattedDateObject){
      if(result.indexOf(key) >= 0){
        /**
         * If the model date doesn't match the required format, then try to fill whatever we can and
         * eliminate the other parameters.
         */
        if(!formattedDateObject[key]){
          result = result.replace(key, '');
          continue;
        }
        result = this.replaceStringAt(result, result.indexOf(key), formattedDateObject[key].toString())
      }
    }
    
    //remove the preceeding and trailing -
    result = result.replace('--', '-').replace(/^-/,'').replace(/-$/,'');
    return result;
  }
  getAddedDaysDateFromDate(date:Date,numDays) {
    if(date)
    return new Date(date.setDate(date.getDate() + numDays))
 }
 getAddedDaysCurrDateFromDate(numDays){
  return new Date(new Date().setDate(new Date().getDate() + numDays))
 }
  getDateInFormatFromDateObject(date: Date, format: string): string{
    if(!date) return '';
    let year = date.getFullYear();
    let month = date.getMonth()+1;
    let day = (date.getDate() == 0)?0:date.getDate();
    
    return this.getDateInFormat(year, month, day, format);;
  }

  /**
   * Convert list of model dates into the required format
   * @param dateList 
   * @param format 
   */
  convertListOfModelDateToFormat(dateList: Array<String>, format: string): Array<String>{
    if(!dateList) return dateList;
    let result = [];

    for(let index=0; index < dateList.length; index++){
      try{
        result.push(this.getDateInFormatFromModelDate(dateList[index].toString(), format));
      }catch(e){
        console.log(e);
        throw e;
      }
    }

    return result;
  }

  getFormattedDecimalFixed(num){
    if(!isNaN(num))
    {
      if( num === parseInt(num) ){
        return num.toLocaleString('en-IN');
        }
        else{
           num=num ? Number(num.toFixed(1)).toLocaleString('en-IN') : '-';
           return num;
        }
    }
  }

  getFormattedDecimalFix(num){
    if(!isNaN(num))
    {
      if( num === parseInt(num) && num > 999){
        return num.toLocaleString('en-IN');
        } else if(num === parseInt(num) && num < 999){
          num = (Math.round(num * 10) / 10).toFixed(1)
          num=num ? Number(num).toLocaleString('en-IN') : '-';
          if(Math.floor(num) == num){
            num = num + '.0' 
          }
          return num; 
        }
        else{
           num = (Math.round(num * 10) / 10).toFixed(1)
           num=num ? Number(num).toLocaleString('en-IN') : '-';
           if(Math.floor(num) == num){
             num = num + '.0'
           }
           return num;
        }
    }
  }

  getDecimalFixedNew(num){
    if(!isNaN(num))
    {
      if( num === parseInt(num) ){
        if(num>999 && num<=9999){
          return num.toString()[0]+','+num.toString().slice(1);
        }else{
          return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") ;
        }               
      }
      else{
        if(num>999 && num<=9999){
          return num ? num.toString()[0]+','+num.toFixed(1).toString().slice(1) : '-';
        }else{
          num=num ? (num.toFixed(1)).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")  : '-';
          return num;
        }
      }
    } 
    return;
  }
  getDecimalFixed(num){
   if(!isNaN(num)){
    return num ? num.toFixed(1) : '-'
   }
   return;
  }

  getTwoDecimalFixed(value){
     if(!isNaN(value))
    {
      value = value.toString().split('e');
      value = Math.round(+(value[0] + 'e' + (value[1] ? (+value[1] + 2) : 2)));
      value = value.toString().split('e');
      return (+(value[0] + 'e' + (value[1] ? (+value[1] - 2) : -2))).toFixed(2);
    }
  }
  //TODO:change last day
  getStartAndEndDatesForType(startDate:string,endDate:string,plan:string){

    let data = {
      "startDate":startDate,
      "endDate":endDate
    }

    if( plan == this.constants.DATEPICKER_DATE_FORMAT.YEAR){
      data["startDate"] = data["startDate"] + "0101";
      data["endDate"] = data["endDate"] + this.getmonthyearforyearType(data["endDate"]);
    }else if( plan == this.constants.DATEPICKER_DATE_FORMAT.MONTH){
      data["startDate"] = data["startDate"] + "01";
      data["endDate"] = this.getLastDayForGivenModelDate(data['endDate']);
    }

      let y1 = data['startDate'].slice(0, 4);
      let m1 = data['startDate'].slice(4, 6);
      let d1 = data['startDate'].slice(6, 8);
      // data['startDate']= d1 + '/' + m1 + '/' + y1;
      data['startDate']=  y1 + m1  + d1;

      y1 = data['endDate'].slice(0, 4);
      m1 = data['endDate'].slice(4, 6);
      d1 = data['endDate'].slice(6, 8);
      // data['endDate']= d1 + '/' + m1 + '/' + y1;
      data['endDate']= y1 + m1  + d1;


    return data;
  }


  getLastandSecondLast(startDate:string,endDate:string,plan:string){
    let data2 = {
      "last":startDate,
      "slast":endDate
    }
    if(plan == this.constants.DATEPICKER_DATE_FORMAT.YEAR)
    {
      let y = parseInt(endDate);
      y = y-1;
      let year= y.toString();
      data2['last'] = year;
       y  = parseInt(endDate);
       y=y-2;
       year = y.toString();
      data2['slast'] = year;    
    }
    if(plan == this.constants.DATEPICKER_DATE_FORMAT.MONTH || plan == this.constants.DATEPICKER_DATE_FORMAT.DAY)
    {
      let y = endDate.slice(0, 4);
      let year = parseInt(y);
      let m = endDate.slice(4, 6);
      let mon = parseInt(m);
      let p1 = this.monthNames[(mon + 12 - 2) % 12].toString();
      let month = this.monthNames[m];
      if (month == 'Jan') {
        year = year - 1;
      }
      y = year.toString().slice(2);

      data2['last'] = p1 + '-' + y ;

      let p2 = this.monthNames[(mon + 12 - 3) % 12].toString();

      if (month == 'Feb' || month == 'Jan') {
        year = year - 1;
      }

      y = year.toString().slice(2);

      data2['slast'] = p2 + '-' + y ;

    }

    return data2;

  }


  gettimestepbyformat(format){
    if(this.constants.DATEPICKER_DATE_FORMAT.YEAR === format)
     return "Yearly";
    else if(this.constants.DATEPICKER_DATE_FORMAT.MONTH == format) 
     return "Monthly";
    else if(this.constants.DATEPICKER_DATE_FORMAT.DAY == format)
     return "Daily"; 
    else if(this.constants.DATEPICKER_DATE_FORMAT.SEASON == format)
     return "Seasonal"; 
  }

  getaggregationtypedisplay(Aggtype){
    if(Aggtype === "SUM")
      return "Cumulative";
    else if(Aggtype === "AVG")
     return "Average";
    else if(Aggtype === "MIN")
     return "Minimum";
    else if(Aggtype === "MAX")
     return "Maximum"; 
    else if(Aggtype === "LATEST")
      return "Latest";

  }

  getmonthyearforyearType(enddate):string{
    var date = new Date();
    if(enddate == date.getFullYear()){
      let month = ((date.getMonth()+1) < 10 ? '0' + (date.getMonth()+1) : '' + (date.getMonth()+1));
      let curdate = (date.getDate() < 10 ? '0' + date.getDate(): '' + date.getDate());
      return (month +""+curdate);
    }else{
      return "1231";
    }
    
  }

  getLastDayForGivenModelDate(date: string,maxDate=null): string{
    if(!date) return date;
    else if(date.length == 4) {
      date = date + this.getmonthyearforyearType(date);
    }
    else if(date.length == 6){
      let currentDate = new Date();
      let year = Number.parseInt(date.substr(0,4));
      let month = Number.parseInt(date.substr(4,2));
      let lastDate = new Date(year, month, 0);
      if(year == currentDate.getFullYear() && month == currentDate.getMonth()+1)
        date = date + (currentDate.getDate() < 10 ? '0' + currentDate.getDate(): '' + currentDate.getDate());
      else
        date = date + ((lastDate.getDate() < 10)?"0"+lastDate.getDate():lastDate.getDate());
  }   

   if(maxDate && maxDate != undefined && date>maxDate){
    date = maxDate;
   }
  //  let currentDate = new Date();
  //  let cD = this.getDateObjectFromModelDate(currentDate.toString());
  // console.log("\n currentDate ", currentDate, "\n date ", date);
  //  if(date>currentDate.toString()){
  //   date = currentDate.toString();
  //  }

    return date;
  }

  clearLocalSessionStorage(){
    let userData = null;
    let data = this.localStorage.get('userDetails');
    console.log("\n clearLocalSessionStorage userData ", data);
    if (data != null && data != undefined && data['data'] != undefined) {
      userData = data['data'];
    }
    this.localStorage.clear();
    if(userData != null){
      this.setUserDetails(userData);
    }
  }

  setUserDetails(userDetails) {
    this.localStorage.set('userDetails', {
      data:userDetails
    });
  }

  getRandomColor(opacity: number): string{
    let r = (Math.floor(Math.random() * 255) + 0).toString(),
        g = (Math.floor(Math.random() * 255) + 0).toString(),
        b = (Math.floor(Math.random() * 255) + 0).toString();

    return "rgba("+r+","+g+","+b+","+opacity+")";
  }

  isChartDataEmpty(data: object): boolean{
    if(!data || !data['data'] || data['data'].length == 0 || !data['data'][0] || !data['data'][0]['data'] ||data['data'][0]['data'].length==0)  return true;
    return false;
  }
  
  isTableDataEmpty(data: object): boolean{
    if(!data || !data['dataSource'] || data['dataSource'].length == 0 || Object.keys(data['dataSource']).length == 0) return true;
    return false;
  }

  addNDaysToModelDate(date: number, numOfDays: number){
    if(date.toString().length < 8)  return;
    return this.getModelDateFromDate(this.addNDaysToDateObject(this.getDateObjectFromModelDate(date.toString()), numOfDays));
  }

  addNDaysToDateObject(dateObj: Date, numOfDays: number): Date{
    if(!dateObj)  return null;
    return new Date(dateObj.setTime( dateObj.getTime() + numOfDays * 86400000 ));
  }

  downloadFile(data: any, filename: string) {
    let EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
    // let EXCEL_TYPE2 = "application/vnd.ms-excel"
    if(!data) return;
    let blob = new Blob([data], {type: EXCEL_TYPE});
    let objectUrl = window.URL.createObjectURL(blob);
    let a = document.createElement('a');
    a.href = objectUrl;
    a.download = filename;
    a.target = '_blank';
    a.click();
  }

  addEachPermanentParamsToURL(params: Object, options: Object): any {
    params = this.getWritableObject(params);
    Object.assign(params, {
      "locname": options['locname']
      , "locuuid": options['locuuid']
      , "loctype": options['loctype']
      , "view": options['view']
      , "component": options['component']
    });

    return params;
  }

  getCurMon(d) {
    let y1 = d.slice(0, 4);
    let m1 = d.slice(4, 6);
    let d1 = d.slice(6, 8);
    let date= d1 + '/' + m1 + '/' + y1;
    if (this.pattern.test(date))
    return date;
  }

  getLastMon(d) {
    let m = this.monthNames[d.getMonth()].toString();
    let p = this.monthNames[(d.getMonth() + 12 - 1) % 12].toString();
    let y = d.getFullYear();
    let y1;
    if (m == 'Jan') {
      y = y - 1;
    }
    y1 = y.toString().slice(2);
    return (p + '-' + y1);
  }


  getLastMonth(d){
    let a =  parseInt(d.slice(4,6)); 
    let m = this.monthNames[a-2];
    let y = parseInt(d.slice(2,4));
    if(a==1)
    {  m = 'Dec';
      y = y - 1; 
    }  
    let year;
    year =y.toString();
    if(y<10)
    {
      year = '0'+year;
    }
    return (m + '-' + year);
  }

  getSecondLastMonth(d){
    let a =  parseInt(d.slice(4,6));    
    let m = this.monthNames[a-3];
    let y = parseInt(d.slice(2,4));
    if(a==1)
    { m = 'Nov';
      y = y - 1; 
    }  
    if(a==2)
    { m = 'Dec'; 
      y = y - 1; 
    } 
    let year;
    year =y.toString();    
    if(y<10)
    {
      year = '0'+year;
    }
    return (m + '-' + year);

  }
  
  getSecLastMon() {
    const d = new Date();
    let m = this.monthNames[d.getMonth()].toString();
    let p2 = this.monthNames[(d.getMonth() + 12 - 2) % 12].toString();
    let y = d.getFullYear();
    let y1;
    if (m == 'Feb' || m == 'Jan') {
      y = y - 1;
    }
    y1 = y.toString().slice(2);
    return (p2 + '-' + y1);
  }
  
  getMonsoonYear(d1) {
    const d = new Date();
    var y = d.getFullYear();
    (d.getMonth() < 5) ? y = y - 1: y = y;
    return ('01/06/' + y + ' to ' + this.getCurMon(d1));
  }

  getCurrentWaterYearStartDate()
  {
    const d = new Date();
    var y = d.getFullYear();
    (d.getMonth() < 5) ? y = y - 1: y = y;
    return ('Jun 01, ' + y); 

  }
  getWaterYearStartDate(date){
    const d = this.getDateObjectFromModelDate(date);
    var y = d.getFullYear();
    (d.getMonth() < 5) ? y = y - 1: y = y;
    return (y+'0601'); 
  }
  getWaterYearFromModelDate(date){
    const d = this.getDateObjectFromModelDate(date);
    var y = d.getFullYear();
    (d.getMonth() < 5) ? y = y - 1: y = y;
    return y; 
  }

  getCurrentMonsoonrYearStartDate()
  {
    const d = new Date();
    var y = d.getFullYear();
    (d.getMonth() < 5) ? y = y - 1: y = y;
    return (y+'0601'); 
  }

  getCurrentWaterYear()
  {
    const d = new Date();
    var y = d.getFullYear();
    (d.getMonth() < 5) ? y = y - 1: y = y;
    return y; 
  }

  getStartOfMonsoon(yearBack: number): Date{
    let currDate = new Date();
    currDate.setDate(currDate.getDate() - 365 * yearBack);

    let monsoonYear = currDate.getFullYear();
    if((currDate.getMonth()+1) < 6)
      monsoonYear -= 1;

    return new Date(monsoonYear, 5, 1);
  }

  setWaterYear(maxDate: Date, minDate: Date)
  {
    var defaultFormat, defaultEDate, defaultSDate;
    if(minDate&&maxDate){
      var date = new Date(maxDate.getFullYear(),5,1);
      if(date<minDate)
      date=minDate;
      if(date<=maxDate)
      {
          defaultSDate = date;
          defaultEDate = maxDate;
      }
      else
      {
          defaultEDate = maxDate;
          date = new Date(defaultEDate.getFullYear()-1,5,1)
          defaultSDate = date<minDate?minDate:date;
      }
      defaultFormat = this.getNoOfMonths(defaultSDate, defaultEDate)<=4?this.constants.DATEPICKER_DATE_FORMAT.DAY:this.constants.DATEPICKER_DATE_FORMAT.MONTH;
    }
    return {
      "format" : defaultFormat,
      "defaultSDate": defaultSDate,
      "defaultEDate": defaultEDate
    }
  }

  getNoOfMonths(sDate:Date, eDate: Date)
  {
      let noOfMonths = (eDate.getFullYear() - sDate.getFullYear())*12;
      noOfMonths-=sDate.getMonth();
      noOfMonths+=eDate.getMonth()+1;
      return noOfMonths <= 0 ? 0 : noOfMonths;
  }

  getDateDiff(date1str:string,date2str:string){
    let date1 = new Date(date1str);
    let date2 = new Date(date2str);
    let diffTime = Math.abs(date2.getTime() - date1.getTime());
    let diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)); 
    return diffDays;
  }

  fixedEncodeURIComponent(str) : string{
      return encodeURI(str).replace(/\(/g, "%28").replace(/\)/g, "%29");
  }

  formatParamsForBreadcrumb(component, urlParams){
    var str = Object.keys(urlParams).map(function(key){ 
      return encodeURIComponent(key) + '=' + encodeURIComponent(urlParams[key]); 
    }).join(';');
    str = "/" + component + ";" + str;
    return str;  
  }

  convertURLStringToObject(url) {
    var obj = {};
    var pairs = url.split(';');
    for (let i in pairs) {
      var split = pairs[i].split('=');
      obj[decodeURIComponent(split[0])] = decodeURIComponent(split[1]);
    }
    return obj;
  }

  getTSForModelDate(date: string): number{
    if(date == undefined || date.length < 8) return 0;

    let dateObj = new Date(Number.parseInt(date.substring(0,4)), Number.parseInt(date.substring(4,6))-1, Number.parseInt(date.substring(6,8)));
    return dateObj.getTime();
  }
  getNumberOfDaysBetweenTwoModelDates(startDate: string, endDate: string, format: string): number{
    
    let startEndDates = this.getStartAndEndDatesForType(startDate, endDate, format);
    let noOfDays = 0;
    if(startEndDates != undefined && startEndDates['startDate'] != undefined && startEndDates['endDate'] != undefined){
      let differenceInMillis = Math.abs(this.getTSForModelDate(startEndDates['endDate']) - this.getTSForModelDate(startEndDates['startDate']));
      noOfDays = differenceInMillis/this.constants.MILLIS_IN_A_DAY;
    }
    
    return noOfDays;
  }


  // getDownloadChartTitle(format: any, component:any, locname: any, sDate: any, eDate: any)
  // { 
  //   return `${this.gettimestepbyformat(format)} ${this.getComponentName(component)} trends for ${
  //     this.capitalize(locname)} from ${this.getDateInFormatFromModelDate(this.getStartAndEndDatesForType(sDate, eDate, format)['startDate'], 'dd-mmm-yyyy')} to ${
  //     this.getDateInFormatFromModelDate(this.getStartAndEndDatesForType(sDate, eDate, format)['endDate'], 'dd-mmm-yyyy')}`;
  // }
  isEmpty(data : any):boolean{
    return data?true:false;
  }

  // getseasonlist(component: any):object[]{
  //   switch(component){
  //     case this.constants.COMPONENT.GROUNDWATER:
  //       return [{"key":"season1","value":"Jan- Mar","show":false,"months":"01-03"},{"key":"season2","value":"April - June","show":false,"months":"04-06"},{"key":"season3","value":"July - Sep","show":false,"months":"07-09"},{"key":"season4","value":"Oct - Dec","show":false,"months":"10-12"}]
  //       break;
  //     case this.constants.COMPONENT.SWATERQUALITY:
  //       return [{"key":"season1","value":"June-Sept","show":false,"months":"06-09"},{"key":"season2","value":"Oct-Jan","show":false,"months":"10-01"},{"key":"season3","value":"Feb-May","show":false,"months":"02-05"}]
  //       break;
  //   }
  // }

  // getseasontitle(seasontype,year, component){
  //   let seasonlist = this.getseasonlist(component);
  //   let index  = seasonlist.findIndex(data => data['key'] == seasontype)
  //   return seasonlist[index]['value'] +" "+year;
  // }

  // getSeasonType(year: number, component:any):string{
  //   let currDate = new Date();
  //   let currYear = currDate.getFullYear();
  //   let month = currDate.getMonth();
  //   let type: any;

  //   switch(component){
  //     case this.constants.COMPONENT.GROUNDWATER:
  //         type="4";
  //         if(year&&year == currYear)
  //           type = (Math.floor(month/3) + 1).toString();
  //         break;

  //     case this.constants.COMPONENT.SWATERQUALITY:
  //         if(year == currYear)
  //         {
  //           if(month>=5&&month<=8)
  //             type="1";
  //           else if(month>=9&&month<=11)
  //             type="2";
  //           else 
  //             type ="-1";
  //         }   
  //         else if(year == currYear-1&&month == 0)
  //             type="2";

  //         else type="3";
  //         break;
  //   }
  //   return type;
  // }

  getPrecisedValue(value: any, unit:any)
  {
    if(value&&typeof(value) == 'number')
    {
      if(unit&&unit=='mm')
        value = Number.isInteger(value)? value:value.toFixed(1);
        // value = value.toFixed(0);
      else if(unit&&(unit=='m'||unit=='cm'))
        value = Number.isInteger(value)? value:value.toFixed(3);
    }
    return value;
  }

  getAppropriateTimeStep(startDate: string, stopDate: string){
    if(startDate == undefined || stopDate == undefined || startDate.length < 8 || stopDate.length < 8) return "yyyyMM";
    let numOfDays = this.getNoOfDaysBetweenTwoEventGenDay(parseInt(startDate), parseInt(stopDate));
    if(numOfDays <= 120) return "yyyyMMdd";
    else if(numOfDays <= 366)  return "yyyyMM";
    else  return "yyyy";
  }

  getNoOfDaysBetweenTwoEventGenDay(startDate: number, endDate:number) {
			
    let count = 0;
    for (let day = startDate; day <= endDate; ) {
      count++;
      day = parseInt(this.addNDaysToModelDate(day, 1));
    }
    
    return count;
  }


  
  // updateInputDataForBreadcrumb(currentUrlParams={},inputForBreadcrumb =[]){
    
  //   let isFilter = 'false';
  //   if(currentUrlParams['isFilter'] && currentUrlParams['isFilter']== 'true'){
  //      isFilter = 'true';
  //   }
    
  //   if(inputForBreadcrumb.length>0 && isFilter == 'false'){
  //     let lastLocation = inputForBreadcrumb[inputForBreadcrumb.length-1];
  //     let indexOfBreadcrumb = this.constants.LOCATION_LEVEL[currentUrlParams['view']].indexOf(lastLocation['locationType']);
  //     let indexOfUrlLocationType = this.constants.LOCATION_LEVEL[currentUrlParams['view']].indexOf(currentUrlParams['pType']);
  //     if(indexOfBreadcrumb !=-1 && indexOfUrlLocationType != -1){
  //       let locationObj ={
  //         locationName:currentUrlParams['locName'],
  //         locationType:currentUrlParams['pType'],
  //         locationUUID:currentUrlParams['locUUID']
  //       }
  //       let copyOfBreadCrumbData = [];
  //       for(let index =0;index<inputForBreadcrumb.length;index++){
  //         copyOfBreadCrumbData.push(inputForBreadcrumb[index]);
  //       }
  //       if(indexOfUrlLocationType > indexOfBreadcrumb){
  //         copyOfBreadCrumbData.push(locationObj);
  //         inputForBreadcrumb = undefined;
  //         inputForBreadcrumb = copyOfBreadCrumbData;       
  //       }
  //       else if (indexOfUrlLocationType < indexOfBreadcrumb){
  //         let indexOfUrlLocationInBreadcrumb = inputForBreadcrumb.findIndex(obj => obj['locationUUID'] == locationObj['locationUUID']);
  //         if(indexOfUrlLocationInBreadcrumb !=-1){
  //           copyOfBreadCrumbData = [];
  //           for(let index =0;index<=indexOfUrlLocationInBreadcrumb;index++){
  //             copyOfBreadCrumbData.push(inputForBreadcrumb[index]);
  //           }
  //         inputForBreadcrumb = undefined;
  //           inputForBreadcrumb = copyOfBreadCrumbData;
  //         }else{
  //           copyOfBreadCrumbData = [];
  //           for(let index =0;index<indexOfUrlLocationType;index++){
  //             copyOfBreadCrumbData.push(inputForBreadcrumb[index]);
  //           }
  //           copyOfBreadCrumbData[indexOfUrlLocationType]=locationObj;
  //           inputForBreadcrumb = undefined;
  //           inputForBreadcrumb = copyOfBreadCrumbData; 
  //         }
  //       }else{
  //         copyOfBreadCrumbData[copyOfBreadCrumbData.length-1]=locationObj;
  //         inputForBreadcrumb = undefined;
  //         inputForBreadcrumb = copyOfBreadCrumbData;     
  //       }
  //     }
  //   }else{
  //     let breadCrumbData = [];
  //     breadCrumbData.push(this.defaultLocationObj);
  //     if(inputForBreadcrumb.length != 0){
  //       if(currentUrlParams['pType'] != this.constants.LOCATION_CONSTANT.STATE){
  //         let locationObj ={
  //           locationName:currentUrlParams['locName'],
  //           locationType:currentUrlParams['pType'],
  //           locationUUID:currentUrlParams['locUUID']
  //         };
  //         breadCrumbData.push(locationObj);
  //       }
  //     }
  //     inputForBreadcrumb = undefined;
  //     inputForBreadcrumb = breadCrumbData;
  //   }
  //   return inputForBreadcrumb;
  // }




  deleteMaheFromTableData(data=[],uuidColName='uuid'){
    let maheUUID = "0d2a8063-c944-4b01-a5e5-9401cc32cddf";
    let index = data.findIndex(obj=>obj[uuidColName] == maheUUID);
    if(index >-1){
      data.splice(index,1);
    }
    return data;
  }
  deletePeriyarFromTableData(data=[],uuidColName='uuid'){
    let PeriyarUUID = "154ad65b-c88e-4e00-bbf6-3fdc4f5bd68f";
    let index = data.findIndex(obj=>obj[uuidColName] == PeriyarUUID);
    if(index >-1){
      data.splice(index,1);
    }
    return data;
  }
  deleteMaheFromBulletin(data=[],hidemahe='name'){
    let name= 'Mahe'
    let index = data.findIndex(obj=>obj[hidemahe] == name);
    if(index >-1){
      data.splice(index,1);
    }
    return data;
  }
  deleteBasinMaheFromTableData(data=[],uuidColName='uuid'){
    let maheUUID = "a0929fc2-2fc3-44f6-8cbf-e8e93d7394e7";
    let index = data.findIndex(obj=>obj[uuidColName] == maheUUID);
    if(index >-1){
      data.splice(index,1);
    }
    return data;
  }


  getUrlParamsForLocationTypeList(source={}, view, component){
    let urlParams = {
      component: component ,
      srcUUID: source['agencyUUID'],
      pEUUID: this.constants.KERALA_UUID,
      view: view + "_HIERARCHY"
    };
    return urlParams;
  }

  updateLocationTypeList(locTypeList,component,urlParams) {
    if(component == this.constants.COMPONENT.LI_SCHEMES){
      locTypeList.pop();
      let index = locTypeList.findIndex(locType => locType == this.constants.LOCATION_CONSTANT.WATERSHED);
      if(index != -1){
        locTypeList.splice(index,1);
      }
    }else if(component== this.constants.COMPONENT.MI_TANKS ){
      locTypeList.pop();
    }else if(component == this.constants.COMPONENT.SOILMOISTURE && urlParams['view'] == "BASIN_HIERARCHY"){
      locTypeList = ["STATE" , "BASIN", "SUBBASIN", "WATERSHED"];
    }else if(component == this.constants.COMPONENT.TEMPERATURE && urlParams['view'] == "BASIN_HIERARCHY"){
      locTypeList = ["STATE" , "BASIN", "SUBBASIN","WATERSHED"];
    }else if(component == this.constants.COMPONENT.GROUNDWATER && urlParams['view'] == "BASIN_HIERARCHY"){
      locTypeList = ["STATE" , "BASIN", "SUBBASIN","WATERSHED"];
    }
    return locTypeList;
  }


  sortLocationData(locationList=[]){
    locationList.sort((a,b) => a['locationName']>b['locationName']?1:-1);
    return locationList;
  }

  formatLocationData(data) {
    let locList = [];
    let allLocationList = Object.values(data);
    for (let index = 0; index < allLocationList.length; index++) {
      locList = locList.concat(allLocationList[index]);
    }
    return locList;
  }


  checkComponent(currentUrlParams,component){
    let valid = false;
    if(currentUrlParams['component'] && currentUrlParams['component'] == component){
      valid = true;
    }
    return valid;
  }

changeMISToGISTableConfig(tableConfig){
    tableConfig['searchFilter']={
      display: true,
      align: 'left',
      order: 'order-md-1'
    };
   tableConfig['tableInfo']={
   title: {
     display: false,
     align: 'center',
     order: 'order-md-1'
   },
  };
  return tableConfig;
  }

   getSourceForIndustry(){
     let sList = [{
      key: "STATE_AND_CENTRAL_STATION",
      value: "All Agencies",
      locUUID: "b3a3109c-1d8d-11ea-9f34-04ea56f9ded6",
      locType: "STATE",
      locName: "Kerala",
      agencyUUID: "92a8d6a5-83d1-11ea-ad17-000d3a320689",
      hasStation: false,
      minmax: {MIN: 20190101, MAX: 20191231}
      }]
      return sList;
   }

   getLocationTypesForIndustry(){
     let locTypeList = [
      this.constants.LOCATION_CONSTANT.STATE,this.constants.LOCATION_CONSTANT.DISTRICT
     ]
     return locTypeList;
   }

   getSourceForWaterBudget(){
    let sList = [{
     key: "STATE_AND_CENTRAL_STATION",
     value: "All Agencies",
     locUUID: "b3a3109c-1d8d-11ea-9f34-04ea56f9ded6",
     locType: "STATE",
     locName: "Kerala",
     agencyUUID: "92a8d6a5-83d1-11ea-ad17-000d3a320689",
     hasStation: false,
     minmax: {MIN: 20190101, MAX: 20191231}
     }]
     return sList;
  }

  getSourceForWaterAudit(){
    let maxDate = this.getTodayAsModelDate();
    let sList = [{
      key: "STATE_AND_CENTRAL_STATION",
      value: "All Agencies",
      locUUID: "b3a3109c-1d8d-11ea-9f34-04ea56f9ded6",
      locType: "STATE",
      locName: "Kerala",
      agencyUUID: "92a8d6a5-83d1-11ea-ad17-000d3a320689",
      hasStation: false,
      minmax: {MIN: 20190101, MAX: maxDate}
    }];
    return sList;
  }

  getLocationTypesForPanchayatWb(){
    let locTypeList = [
     this.constants.LOCATION_CONSTANT.STATE,this.constants.LOCATION_CONSTANT.DISTRICT,
     this.constants.LOCATION_CONSTANT.MANDAL
    ]
    return locTypeList;
  }

  getLocationTypesForBasinWb(){
    let locTypeList = [
     this.constants.LOCATION_CONSTANT.STATE,this.constants.LOCATION_CONSTANT.BASIN,
     this.constants.LOCATION_CONSTANT.SUBBASIN, this.constants.LOCATION_CONSTANT.WATERSHED
    ]
    return locTypeList;
  }

  getLocationTypeForWA(urlParams) {
    let locType = [];
    if(urlParams['view'] == this.constants.VIEWS.ADMIN + '_HIERARCHY') {
      locType = [
        this.constants.LOCATION_CONSTANT.STATE,this.constants.LOCATION_CONSTANT.DISTRICT,
        this.constants.LOCATION_CONSTANT.MANDAL
      ] 
    }else {
      locType = [
        this.constants.LOCATION_CONSTANT.STATE,this.constants.LOCATION_CONSTANT.BASIN,
        this.constants.LOCATION_CONSTANT.SUBBASIN
      ]
    }
    return locType;
  }

   checkDataAvailability(data) {
     if(typeof data == 'object') {
       return !this.isEmptyObj(data);
     } else {
       if(data && data.length > 0) {
         return true;
       } else {
        return false;
       }
     }
   }


   checkExternalSystem(params){
    // console.log("\n From utils checkExternalSystem \n params :", params);
    if(params && params['system'] && params['system'].toLowerCase() =='idrb'){
      this.constants.IS_EXTERNAL_SYSTEM = true;
    } else if(params && params['system'] && params['system'].toLowerCase() =='embeded'){
      this.constants.IS_EXTERNAL_SYSTEM = true;
      this.constants.IS_RAINFALL_EMBEDED_TABLE = true;
    }
   }

   checkWhetherComponentArrSpecificUserApplies(compArr,dashboardType){
    let showComponentWRTComponentSpecificUser = false;
    for(let comp of compArr){
      showComponentWRTComponentSpecificUser = this.checkWhetherComponentSpecificUserApplies(comp, dashboardType);
      if(showComponentWRTComponentSpecificUser == true){
        break;
      }
    }
    return showComponentWRTComponentSpecificUser;
  }

   checkWhetherComponentSpecificUserApplies(component,dashboadType){
    let isComponentSpeciifcUser = this.constants.IS_COMPONENT_SPECIFIC_USER;
    // console.log("\n In checkWhetherComponentSpecificUserApplies component ",component,"\n dashboadType ",dashboadType,
    // "IS_COMPONENT_SPECIFIC_USER",this.constants.IS_COMPONENT_SPECIFIC_USER);
    let showComponentWRTComponentSpecificUser = true;
    if(isComponentSpeciifcUser){
  //    console.log("\n In checkWhetherComponentSpecificUserApplies ",this.constants.COMPONENTS_INFO);
      showComponentWRTComponentSpecificUser = false;
      if(this.constants.COMPONENTS_INFO[component]['ACCESS'][dashboadType] == true){
        showComponentWRTComponentSpecificUser = true;
      }
    }
    return showComponentWRTComponentSpecificUser;
  }

  checkWhetherComponentSpecificUserDashboardApplies(dashboard,dashboardType){
    let isComponentSpeciifcUser = this.constants.IS_COMPONENT_SPECIFIC_USER;
    let showDashboardWRTComponentSpecificUser = true;
    if(isComponentSpeciifcUser){
      showDashboardWRTComponentSpecificUser = false;
      if(this.constants.COMPONENT_USER_SPECIFIC_DASHBOARD_STATUS[dashboardType][dashboard] == true){
        showDashboardWRTComponentSpecificUser = true;
      }
    }
    return showDashboardWRTComponentSpecificUser;
  }

  checkWhetherComponentSpecificUserNavigationApplies(component,fromDashboardType,toDashboardType){
    let isComponentSpeciifcUser = this.constants.IS_COMPONENT_SPECIFIC_USER;
    let navigateDashboardWRTComponentSpecificUser = true;
    if(isComponentSpeciifcUser){
      navigateDashboardWRTComponentSpecificUser = false;
      if(fromDashboardType == "GIS"){
        if(this.constants.DASHBOARDS_COMPONENT_ROUTE_MAPPING[fromDashboardType][component]){
          component = this.constants.DASHBOARDS_COMPONENT_ROUTE_MAPPING[fromDashboardType][component];
        }
      }
      if(this.constants.COMPONENTS_INFO[component]){
        if(this.constants.COMPONENTS_INFO[component]['ACCESS'][toDashboardType] == true){
          navigateDashboardWRTComponentSpecificUser = true;
        }
      }
    }
    return navigateDashboardWRTComponentSpecificUser;
  }

  updateMinMaxDateForSWq(sourceList){
    for (let src of sourceList) {
      let minmax = src['minmax'];
      if(minmax != undefined && minmax['MIN'] != undefined && minmax['MIN'] != null && minmax['MAX'] != undefined && minmax['MAX'] != null && minmax['MIN'] != 0 && minmax['MAX'] != 0){
        minmax['MIN'] =  minmax['MIN']+'0601';
        minmax['MAX'] =  minmax['MAX']+'1231';
        // minmax['MAX'] = (Number(minmax['MAX']) +1 )+'0531';
        let todayModelDate = this.getTodayAsModelDate();
        if( minmax['MAX'] > todayModelDate){
          minmax['MAX'] = todayModelDate;
        }
      }
    }
    // console.log("\n In updateMinMaxDateForSWq  ",sourceList);
    return sourceList;
  }

  updateSourceList(sourceList) {
    let todayModelDate = this.getTodayAsModelDate();
    for (let src of sourceList) {
      let minmax = src['minmax'];
      if (!minmax || (minmax['MIN'] != undefined && minmax['MIN'] != null && minmax['MAX'] != undefined && minmax['MAX'] != null && minmax['MIN'] == 0 && minmax['MAX'] == 0)) {
        let currentDate = new Date();
        let month = currentDate.getMonth(), currentYear = currentDate.getFullYear();
        if (month < 5) {
          currentYear = currentYear - 1;
        }
        minmax['MIN'] = currentYear + '0601';
        minmax['MAX'] = todayModelDate;
      } else if (minmax['MIN'] != undefined && minmax['MIN'] != null &&
        minmax['MAX'] != undefined && minmax['MAX'] != null) {
        if (minmax['MIN'] == 0 && minmax['MAX'] != 0) {
          let currentMaxDate = this.getDateObjectFromModelDate(minmax['MAX'].toString());
          let month = currentMaxDate.getMonth(), currentYear = currentMaxDate.getFullYear();
          if (month < 5) {
            currentYear = currentYear - 1;
          }
          minmax['MIN'] = currentYear + '0601';
        } else if (minmax['MIN'] != 0 && minmax['MAX'] == 0) {
          minmax['MAX'] = todayModelDate;
        }
      }
    }
    // for(let src of sourceList){
    //   let minmax = src['minmax'];
    //   if(minmax['MIN'] == 0 || minmax['MAX'] == 0){
    //     let todayModelDate = this.utils.getTodayAsModelDate();
    //     let currentDate = new Date();
    //     let month = currentDate.getMonth(),currentYear = currentDate.getFullYear(); 
    //     if(month < 5){
    //       currentYear = currentYear -1;
    //     } 
    //     minmax['MIN'] = currentYear +'0601';
    //     minmax['MAX'] = todayModelDate;
    //   }
    // }
    return sourceList;
  }

  checkAndUpdateSourceListForComponentSpecifcUser(sourceList,urlCompRoute){
    sourceList = this.updateSourceList(sourceList);
    let updatedSourceList =[];
    if(this.constants.IS_COMPONENT_SPECIFIC_USER){
      if(this.constants.COMPONENTS_INFO[urlCompRoute]){
        if(this.constants.COMPONENTS_INFO[urlCompRoute]['SOURCE_LIST'] && this.constants.COMPONENTS_INFO[urlCompRoute]['SOURCE_LIST'].length>0){
          let targetSourceList = this.constants.COMPONENTS_INFO[urlCompRoute]['SOURCE_LIST'] || [];
          if(this.constants.COMPONENTS_INFO.ALL_COMPONENT_SOURCES && this.constants.COMPONENTS_INFO.ALL_COMPONENT_SOURCES.length>0){
            for(let sourceObj of this.constants.COMPONENTS_INFO.ALL_COMPONENT_SOURCES){
              targetSourceList.push(sourceObj);
            }
          }
          // console.log("\n targetSourceList ",targetSourceList);
          for(let srcKey of targetSourceList){
            let index = sourceList.findIndex(src=>src['key'] == srcKey);
            if(index>-1){
              updatedSourceList.push(sourceList[index]);
            }
          }

        }else{
          updatedSourceList = sourceList;
        }
      }
    }else{
      updatedSourceList = sourceList;
    }
    if(updatedSourceList.length ==0){
      updatedSourceList = sourceList;
      // console.log("\n Fall BackCondition executed in checkAndUpdateSourceListForComponentSpecifcUser \n");
    }
    // console.log("\n checkAndUpdateSourceListForComponentSpecifcUser sourceList ",sourceList,"\n urlCompRoute",urlCompRoute,"\n updatedSourceList ",updatedSourceList);
     return updatedSourceList;
  }

   foramtDateAsPerFormat(date,format) {
    if(!date) return null;

    date = date.toString();
    let year = date.substring(0,4);
    let month = date.substring(4,6)?Number(date.substring(4,6)): 1;
    let day = date.substring(6,8)? date.substring(6,8): '01';
    if(format == 'yyyyMMdd') {
      return this.monthNames[month - 1] + ' ' + day + ',' + year;
    }else if(format == 'yyyyMM') {
      return this.monthNames[month - 1] + ',' + year;
    }else if(format == 'yyyy') {
      return year;
    }
    return '-';
   }

  getTimefromTimemills(millis){
    if(!this.isNullAndUndefined(millis)) return '-';

    let time = parseInt(millis);
    let date = new Date((time/1000) *1000);
    //to covert to IST 
    date.setHours(date.getHours() + 5); 
    date.setMinutes(date.getMinutes() + 30);

    return date.toISOString().slice(11, 16);
  }

   isNullAndUndefined(data) {
    if(data == null || data == undefined) {
      return false;
    }else {
      return true;
    }
   }

   getWaterYearForData(date) {
    let currentYear = Number(date.substr(0, 4));
    let currentMonth = Number(date.substr(4, 2));
    if (currentMonth < Number(6)) {
      currentYear = currentYear - 1;
    }
    return 'Jun 01, ' + currentYear.toString();
  }
 }
