import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SharedService } from '../shared.service';
import { NavigationEnd, Router } from '@angular/router';
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
  sub1;
  sub2;
  isLoginPage = false;
  isJourneyDetailsPage = false
  isCancelBooking = false
  isBookingHistoryPage = false
  isCartDetailsPage = false

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
    this.isLoginPage = (this.router.url == "/login" || this.router.url == "/")
    this.isJourneyDetailsPage = (this.router.url == "/user/journey-details")
    this.isCancelBooking = (this.router.url == "/user/guest-user-cancel")
    this.isBookingHistoryPage = (this.router.url == "/user/bookingHistory")
    this.isCartDetailsPage = (this.router.url == "/user/cart")
    this.getAndSetUserDetails();
    this.sub1 = this.shdService.headerRefresh.subscribe(val => {
      this.getAndSetUserDetails();
    })
    this.sub2 = this.router.events.subscribe((val) => {
      console.log(val)
      if(val instanceof NavigationEnd) {
        this.isLoginPage = (val.url == "/login"|| this.router.url == "/")
        this.isJourneyDetailsPage = (val.url == "/user/journey-details")
        this.isCancelBooking = (val.url == "/user/guest-user-cancel")
        this.isBookingHistoryPage = (val.url == "/user/bookingHistory")
        this.isCartDetailsPage = (val.url == "/user/cart")
      }
    })
  }

  ngOnDestroy(){
    this.sub1.unsubscribe();
    this.sub2.unsubscribe();
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

  navigateHome(){
    let journeyDetailsFilter = this.localStorage.get('journeyDetailsFilter');
    console.log("\n journeyDetailsFilter data ",journeyDetailsFilter);
    if(journeyDetailsFilter != null && journeyDetailsFilter != undefined && journeyDetailsFilter['data'] != undefined){
      this.router.navigate(["/user/home"]); 
    }else{
      this.router.navigate(["/user/journey-details"]); 
    }
  }


  journeyDetails(){
    this.router.navigate(["/user/journey-details"]); 
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

  cartDetailsPage(){
    this.router.navigate(["/user/cart"]); 
  }

}
