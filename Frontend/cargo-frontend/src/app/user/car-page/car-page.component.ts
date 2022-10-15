import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SharedService } from 'src/app/shared/shared.service';

@Component({
  selector: 'app-car-page',
  templateUrl: './car-page.component.html',
  styleUrls: ['./car-page.component.css']
})
export class CarPageComponent implements OnInit {

  id = ""
  carData:any = {}
  disp= true;
  errorDisp = false;
  constructor(private http: HttpClient, private httpService: SharedService,  private route: ActivatedRoute, private router:Router) {}

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('carId');
    if(this.id )
    if(this.id == null && this.id == undefined || this.id == ""){
      this.router.navigate(["/user/journey-details"]); 
    }
    this.getCardData()
    // if(this.carData == null && this.carData == undefined){
    //   this.router.navigate(["/user/journey-details"]); 
    // }
  }

  async getCardData(){
    this.disp = false;
    this.errorDisp = false;
    let endpoint = "variant_by_id/" + this.id
    let result = await this.httpService.get(endpoint).toPromise();
    if(result != null && result != undefined){
      let sList =result['variantList'];
      if (sList && sList.length > 0) {
        this.carData = sList[0];
      }else{
        this.errorDisp = true;
      }
    }else{
      this.errorDisp = true;
    }
  }

  addToCart() {
    this.router.navigate(["/user/cart"]); 
  }

  bookNow() {
    this.router.navigate(["/user/payment"]); 
  }
}
