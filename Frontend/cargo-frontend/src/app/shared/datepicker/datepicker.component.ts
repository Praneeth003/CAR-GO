import {Component,Input,ViewChild,ElementRef,EventEmitter, HostListener,forwardRef, OnInit,OnChanges, SimpleChanges, Output
} from '@angular/core';
import { ControlValueAccessor,  NG_VALUE_ACCESSOR} from '@angular/forms';
import { BsDatepickerViewMode } from 'ngx-bootstrap/datepicker/models';
import { BsDatepickerConfig } from 'ngx-bootstrap/datepicker/public_api';
import { idLocale} from 'ngx-bootstrap/chronos/i18n/id';
import { UtilsModule } from '../utils/utils/utils.module';

@Component({
  selector: 'datepicker',
  templateUrl: './datepicker.component.html',
  styleUrls: ['./datepicker.component.css'],
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => DatepickerComponent),
    multi: true
  }]
})
export class DatepickerComponent implements OnInit {


  @Input() name: string = '';
  @Input() model: any;
  @Input() minDate: any;
  @Input() maxDate: any;
  @Input() calendarFormat: string;
  @Input() placeHolder: string;
  @Input() disabled: boolean = false;
  @Input() validation: boolean[] = new Array(4).fill(true);
  @Input() from: any;
  @Input() to: any;
  @Output() change = new EventEmitter < Object > ();

  dateFormat: object = {
    year: 'YYYY',
    month: 'MMMM, YYYY',
    day: 'DD-MMMM-YYYY'
  }

  minModeKeyMap = {
    "yyyy": "year",
    "yyyyMM": "month",
    "yyyyMMdd": "day"
  }

  @ViewChild('dp') dp;
  private propagateChange: any = () => {};

  defaultCalendarPlan = 'day';
  minMode: BsDatepickerViewMode = 'day';
  bsConfig: Partial < BsDatepickerConfig > ;

  constructor(private _eref: ElementRef, private utils: UtilsModule) {

  }

  ngOnChanges(changes: SimpleChanges) {
    this.minDate = (changes && changes['minDate']) ? changes['minDate']['currentValue'] : this.minDate;
    this.maxDate = (changes && changes['maxDate']) ? changes['maxDate']['currentValue'] : this.maxDate;

    // if(this.minMode == 'year')
    //   this.minDate.setFullYear(this.minDate.getFullYear()+1);
    // else if(this.minMode == 'month')
    //   this.minDate.setMonth(this.minDate.getMonth()+1);
    // else
    //   this.minDate.setDate(this.minDate.getDate()+1);
    if (changes && changes['calendarFormat'] && changes['calendarFormat']['currentValue']) {
      this.minMode = this.minModeKeyMap[changes['calendarFormat']['currentValue']];
      this.resetCalendarMinMode();
      this.model = undefined;
    }
    if (this.from != undefined)
      this.validation[0] = true;
    if (this.to != undefined)
      this.validation[1] = true;
    if (this.from != undefined && this.to != undefined) {
      this.validation[2] = this.utils.getModelDateWithDateFormat(this.from, this.calendarFormat) >
        this.utils.getModelDateWithDateFormat(this.to, this.calendarFormat) ? false : true;
    }
  }

  ngOnInit() {
    this.resetCalendarMinMode();
  }

  resetCalendarMinMode(): void {
    this.bsConfig = Object.assign({}, {
      minMode: this.minMode,
      dateInputFormat: this.dateFormat[this.minMode]
    });
  }

  @HostListener('document:click', ['$event'])
  onClick(event) {
    if (this._eref.nativeElement.contains(event.target)) return true;
    // setTimeout(() => this.dp.close(), 10);
  }

  onModelChange() {
    this.propagateChange(this.model);
    this.change.emit(this.model);
  }

  writeValue(value) {
    this.model = value;
  }

  registerOnChange(fn) {
    this.propagateChange = fn;
  }

  registerOnTouched() {}

}
