import { Injectable } from '@angular/core';
import { of, Observable } from 'rxjs';
import { ConstantsModule } from '../constants.module';


@Injectable({
  providedIn: 'root'
})
export class HttperrorhandlerService {

  constructor(private constant:ConstantsModule) { }
  handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
     if(this.constant.DEBUGGER){
     	// this.toastr.error(error['message'], 'Something went wrong during ' + `${operation}`,{});
     // console.error(error);
      console.log(`${operation} failed: ${error.message}`);
     }
      return of(result as T);
    };
  }
}
