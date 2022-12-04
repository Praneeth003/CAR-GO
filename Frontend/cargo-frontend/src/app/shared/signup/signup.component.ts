import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SharedService } from '../shared.service';
import { Router } from '@angular/router';
import { LocalStorageService } from 'angular-web-storage';
import { ConstantsModule } from '../constants.module';
import { UtilsModule } from '../utils/utils/utils.module';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  signUpFailed:any = false;
  errorMessage = "";
  username:any;
  email:any;
  mNo:any;
  psswd:any;
  reEnterPsswd:any;
  constructor(private http: HttpClient,    private waterDataService: SharedService, private constantsModule:ConstantsModule,
    private utils:UtilsModule,private router: Router,   private localStorage: LocalStorageService ){ }

  ngOnInit() {
  }

  signup() {

    let data = {
      "userName":this.username,
      "userEmail":this.email,
      "userPassword":this.psswd,
      "userMobileNumber": this.mNo
    }
    console.log("data ",data);

    if(this.psswd != this.reEnterPsswd){
      this.errorMessage = "Passwords didn't match.... Please Re enter"
      this.signUpFailed = true;
      return 
    }
    if(this.mNo.length != 10){
      this.errorMessage = "Mobile Number didn't match"
      this.signUpFailed = true;
      return 
    }

  
    this.http.post<any>('http://localhost:8081/user/register', data).subscribe(resp => {
      console.log(resp)
      if(resp.status == "SUCCESS"){
        this.setUserDetails(resp['userDetails']);

        this.waterDataService.userName.next( {userName:resp.userDetails.userName,authId:resp.userDetails.authId} );

        let selectedCartData = this.localStorage.get('selectedCart');
        console.log("\n selectedCartData ",selectedCartData);
        if(selectedCartData != undefined){
          selectedCartData = selectedCartData['data'];
        }

        if(selectedCartData && selectedCartData['variant'] && selectedCartData['variant']['variantId']){
          this.router.navigate(["/user/car/" + selectedCartData['variant']['variantId']]); 
        }else{
          let journeyDetailsFilter = this.localStorage.get('journeyDetailsFilter');
          console.log("\n journeyDetailsFilter data ",journeyDetailsFilter);
          if(journeyDetailsFilter != null && journeyDetailsFilter != undefined && journeyDetailsFilter['data'] != undefined){
            this.router.navigate(["/user/home"]); 
          }else{
            this.router.navigate(["/login"]); 
          }
        }

      }
      else{
        this.localStorage.remove('userDetails');
        this.localStorage.remove('selectedCart');
        this.signUpFailed = true
        this.errorMessage = resp.errorDescription
      }
    })

  }

  setUserDetails(userDetails) {
    this.localStorage.set('userDetails', {
      data:userDetails
    });
  }

}
