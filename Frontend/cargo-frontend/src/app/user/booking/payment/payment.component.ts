import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LocalStorageService } from 'angular-web-storage';
import { SharedService } from 'src/app/shared/shared.service';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component-new.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {

 
  constructor(private http: HttpClient, private httpService: SharedService,  private route: ActivatedRoute, private router:Router, private localStorage: LocalStorageService ) {}

  ngOnInit() {
    let data = this.localStorage.get('userDetails');
    console.log("\n journeyDetailsFilter data ",data);
    if(data == null ||  data == undefined || data['data'] == undefined){
      this.localStorage.remove("userDetails");
      this.router.navigate(["/login"]);
    }
  }

}
