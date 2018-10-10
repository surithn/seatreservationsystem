import {Service, Location} from '../app.service';
import {Component, ViewChild, Output, EventEmitter, Input} from '@angular/core';
import {DxFormComponent} from 'devextreme-angular';
import notify from 'devextreme/ui/notify';

@Component({
  selector: 'admin-pop',
  templateUrl: 'admin-pop.html',
  styleUrls: ['../co-ordinates-pop/co-ordinates-pop.css']
})
export class AdminPopComponent {

  @ViewChild(DxFormComponent) form: DxFormComponent;
  service: Service;

  @Input()
  adminData: Location;

  @Output('popVisible')
  popVisibleChange = new EventEmitter<boolean>();

  constructor(service: Service) {
    this.service = service;
  }

  screen(width: any) {
    return width < 720 ? 'sm' : 'md';
  }

  onFormSubmit() {
    this.service.registerLocation(this.adminData);
    this.popVisibleChange.emit(false);
  };
}
