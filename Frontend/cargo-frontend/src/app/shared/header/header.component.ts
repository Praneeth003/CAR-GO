import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SharedService } from '../shared.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { LocalStorageService } from 'angular-web-storage';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  loginMessage = ""
  uN : any  = {};
  displayCancelUser = true
  isAdmin = false

  constructor(private shdService : SharedService, private http : HttpClient, private router: Router,
    private toastrService: ToastrService, private localStorage: LocalStorageService   ) { }

  ngOnInit() {
    // this.shdService.userName.subscribe(uN => {
    //     this.uN =uN;
    //     if(uN.userName == "") {
    //       this.loginMessage = "Login"
    //     }
    //     else {
    //       this.displayCancelUser = false
    //       this.loginMessage = "Logout"
    //     }
    // })
    this.shdService.headerRefresh.subscribe(val => {
      this.getAndSetUserDetails();
    })
    
  }

  getAndSetUserDetails(){
    let data = this.localStorage.get('userDetails');
    console.log("\n userDetails data ", data);
    if (data != null && data != undefined && data['data'] != undefined) {
      this.uN = data['data'];
    }
    if(this.uN['userId'] == null || this.uN['userId'] == undefined){
      this.loginMessage = "Login";
    }else{
      this.displayCancelUser = false;
      console.log(this.uN)
      if(this.uN['userType'] == "ADMIN"){
        this.isAdmin = true;
      }
      this.loginMessage = "Logout";
    }


  }


  login() {
    if(this.loginMessage == "Logout"){
      this.http.post<any>('http://localhost:8081/user/logOut', {authId:this.uN.authId}).subscribe(resp => {
        console.log(resp)
        if(resp.status == "SUCCESS"){
           this.localStorage.remove("userDetails");
           this.localStorage.remove("selectedCart");
           this.uN ={};
           this.isAdmin = false;
           this.displayCancelUser = true;
           this.loginMessage = "Login";


           let journeyDetailsFilter = this.localStorage.get('journeyDetailsFilter');
           console.log("\n journeyDetailsFilter data ",journeyDetailsFilter);
           if(journeyDetailsFilter != null && journeyDetailsFilter != undefined && journeyDetailsFilter['data'] != undefined){
             this.router.navigate(["/user/home"]); 
           }else{
             this.router.navigate(["/user/journey-details"]); 
           }

           this.shdService.userName.next( {userName:"",authId:""} );

           this.toastrService.success("You're successfully logged out", "");
        }
    })
    }
    else {
        this.router.navigate(["/login"]);
    }
  }

  journeyDetails(){
    let journeyDetailsFilter = this.localStorage.get('journeyDetailsFilter');
    console.log("\n journeyDetailsFilter data ",journeyDetailsFilter);
    if(journeyDetailsFilter != null && journeyDetailsFilter != undefined && journeyDetailsFilter['data'] != undefined){
      this.router.navigate(["/user/home"]); 
    }else{
      this.router.navigate(["/user/journey-details"]); 
    }
  }

  cancelBooking(){
    this.router.navigate(["/user/guest-user-cancel"]); 
  }

  bookingHistory(){
    this.router.navigate(["/user/bookingHistory"]); 
  }

  createVariant(){
    this.router.navigate(["/admin/create-variant"]); 
  }

  deleteVariant(){
    this.router.navigate(["/admin/delete-variant"]); 
  }

  finishBooking(){
    this.router.navigate(["/admin/finish-booking"]); 
  }

}
