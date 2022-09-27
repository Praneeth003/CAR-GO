import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { HttperrorhandlerService } from './http/httperrorhandler.service';

@Injectable({
  providedIn: 'root'
})
export class SharedService {

  userName = new BehaviorSubject({userName:"",authId:""});

  urlUser = "http://localhost:8081/user/";

  urlCargo ="http://localhost:8081/cargo/"


  constructor(private http: HttpClient,  private errorHandler: HttperrorhandlerService,) { }

  getLocationList(): Observable<any> {
    return this.http.get<any>(this.urlCargo + "location").pipe(
      catchError(this.errorHandler.handleError('All Location data : ', []))
    );
  }

  post(params, endPoint):Observable<any>{
   return this.http.post<any>(this.urlCargo + endPoint, params).pipe(
      catchError(this.errorHandler.handleError('All post data : ', []))
      );
  }

}

