import {Service} from '../app.service';
import {Component, ViewChild, Output, EventEmitter, Input} from '@angular/core';
import {DxFormComponent} from 'devextreme-angular';

@Component({
  selector: 'co-ordinates-pop',
  templateUrl: 'co-ordinates-pop.html',
  styleUrls: ['co-ordinates-pop.css']
})
export class CoOrdinatesPopComponent {

  @ViewChild(DxFormComponent) form: DxFormComponent;

  colCountByScreen: Object;
  service: Service;

  @Output('popVisible')
  popVisibleChange = new EventEmitter<boolean>();

  @Input()
  deskNo: string;


  constructor(service: Service) {
    this.colCountByScreen = {
      md: 1,
      sm: 2
    };
    this.service = service;
  }

  screen(width: any) {
    return width < 720 ? 'sm' : 'md';
  }

  confirm() {
    this.service.occupyLocation(this.deskNo);
    this.popVisibleChange.emit(false);
  };

  cancel() {
    this.popVisibleChange.emit(false);
  }

}
