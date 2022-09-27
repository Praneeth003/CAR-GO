import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { NgxUiLoaderService } from 'ngx-ui-loader'; // Import NgxUiLoaderService

@Component({
  selector: 'spinner',
  templateUrl: './spinner.component.html',
  styleUrls: ['./spinner.component.css']
})
export class SpinnerComponent implements OnInit, OnChanges {
  
  @Input() id: string;
  @Input() show: boolean;

  constructor(private spinnerService: NgxUiLoaderService) { }

  ngOnChanges(changes: SimpleChanges){
    (changes['show'] && changes['show'].currentValue)?this.startLoader():this.stopLoader();
  }

  ngOnInit() {
  }

  startLoader(): void{
    this.spinnerService.startLoader(this.id);
  }

  stopLoader(): void{
    try{
      this.spinnerService.stopLoader(this.id)
    }catch(e){
    }
  }

}
