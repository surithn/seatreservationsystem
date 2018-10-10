import {Component} from '@angular/core';
import {Service, Product} from '../app.service';
import {isNullOrUndefined} from 'util';
import notify from 'devextreme/ui/notify';

@Component({
  selector: 'my-app-header',
  templateUrl: 'header.template.html',
  styleUrls: ['header.component.css']
})

export class HeaderComponent {

  api: Service;
  products: Product[];
  showSubmenuModes: any;
  showFirstSubmenuModes: any;
  currentProduct: Product;

  constructor(service: Service) {
    this.api = service;
    this.products = service.getProducts();
    this.showSubmenuModes = [{
      name: 'onHover',
      delay: {show: 0, hide: 500}
    }, {
      name: 'onClick',
      delay: {show: 0, hide: 300}
    }];
    this.showFirstSubmenuModes = this.showSubmenuModes[0];
  }

  itemClick(data: any) {
    let item = data.itemData;
    if (!isNullOrUndefined(item.buildingName)) {

      if (item.name === 'Metrics') {
        this.api.metrics = true;
        notify('Metrics clicked!');
        return;
      } else {
        this.api.metrics = false;
      }

      this.api.currentImageSrc = '';
      this.api.currentImageSrc = item.iconSrc;
      this.api.buildingName = item.buildingName;
      this.api.floor = item.floor;
    }

    let text = item.name + ' selected!';
    notify(text);
  }
}
