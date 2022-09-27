import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from 'angular-web-storage';
import { ToastrService } from 'ngx-toastr';
import { ConstantsModule } from '../constants.module';
import { SharedService } from '../shared.service';
import { UtilsModule } from '../utils/utils/utils.module';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username : any;
  psswrd : any;
  signUpFailed:any = false;
  errorMessage:any = ""
  constructor(private http: HttpClient,    private waterDataService: SharedService, private constantsModule:ConstantsModule,
    private utils:UtilsModule,private router: Router,   private localStorage: LocalStorageService,private toastrService: ToastrService ){ }

  ngOnInit() {
  }

  login() {
    console.log(this.username)
    console.log(this.psswrd)
    let data = {
      "userName":this.username,
      "userPassword":this.psswrd
    }
    console.log(data)
  
    this.http.post<any>('http://localhost:8081/user/authenticate', data).subscribe(resp => {
      console.log(resp)
      if(resp.status == "SUCCESS"){
          this.waterDataService.userName.next( {userName:resp.userDetails.userName,authId:resp.userDetails.authId} )
          let data = this.localStorage.get('journeyDetailsFilter');
          console.log("\n journeyDetailsFilter data ",data);
          if(data != null && data != undefined && data['data'] != undefined){
            this.router.navigate(["/user/home"]); 
          }else{
            this.router.navigate(["/user/journey-details"]); 
          }
          this.toastrService.success("You're successfully logged in", "");
        }
      else{
        this.signUpFailed = true
        this.errorMessage = resp.errorDescription;
        this.toastrService.error(resp.errorDescription, "");
      }

    })
  }

}