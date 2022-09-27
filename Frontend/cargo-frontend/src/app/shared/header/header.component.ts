import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SharedService } from '../shared.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  loginMessage = ""
  uN : any  = {};

  constructor(private shdService : SharedService, private http : HttpClient, private router: Router,
    private toastrService: ToastrService   ) { }

  ngOnInit() {
    this.shdService.userName.subscribe(uN => {
        this.uN =uN;
        if(uN.userName == "") {
          this.loginMessage = "Login"
        }
        else {
          this.loginMessage = "Logout"
        }
    })
  }

  login() {
    if(this.loginMessage == "Logout"){
      this.http.post<any>('http://localhost:8081/user/logOut', {authId:this.uN.authId}).subscribe(resp => {
        console.log(resp)
        if(resp.status == "SUCCESS"){
           this.uN ={};
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
    this.router.navigate(["/user/journey-details"]); 
  }

}
