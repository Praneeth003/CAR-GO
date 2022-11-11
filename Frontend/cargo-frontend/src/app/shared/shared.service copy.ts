import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
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

  
  postUser(params, endPoint):Observable<any>{
    return this.http.post<any>(this.urlUser + endPoint, params).pipe(
       catchError(this.errorHandler.handleError('All post data : ', []))
       );
   }

  get(endPoint):Observable<any>{
    return this.http.get<any>(this.urlCargo + endPoint).pipe(
       catchError(this.errorHandler.handleError('All get data : ', []))
       );
   }

   delete(endPoint):Observable<any>{
    return this.http.delete<any>(this.urlCargo + endPoint).pipe(
       catchError(this.errorHandler.handleError('All delete data : ', []))
       );
   }
  
  headerRefresh = new BehaviorSubject(true);
  
  downloadPDF(endPoint) {

    let headerOptions = new HttpHeaders({
        // 'Content-Type': 'application/json',
        'Accept': 'application/pdf'
        //   'Accept': 'application/octet-stream', // for excel file
    });

    let requestOptions = { headers: headerOptions, responseType: 'blob' as 'blob' };
    // post or get depending on your requirement
    return this.http.get(this.urlCargo + endPoint, requestOptions).pipe(map((data: any) => {

        let blob = new Blob([data], {
            type: 'application/pdf' // must match the Accept type
            // type: 'application/octet-stream' // for excel 
        });
        var link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.download = 'samplePDFFile.pdf';
        link.click();
        window.URL.revokeObjectURL(link.href);

    }));

}

}

