import {Service, MetricsProduct} from './app.service';
import {ImgMapComponent} from './ng2-img-map';
import {Component, ViewChild, OnInit} from '@angular/core';

@Component({
  selector: 'demo-app',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {

  @ViewChild('imgMap')
  imgMap: ImgMapComponent;
  service: Service;
  values: MetricsProduct[];

  constructor(service: Service) {
    this.service = service;
    this.service.getXsrfToken();
    this.service.getUserService();
  }

  ngOnInit(): void {
    this.service.getAllLocationList('B');
    this.service.getXsrfToken();
    this.productsToValues();
  }

  customizeTooltip(arg: any) {
    return {
      text: arg.valueText
    };
  }

  productsToValues() {
    let values: any[] = [];

    this.service.metricProducts.forEach(function(product) {
      if (product.active) {
        values.push(product.count);
      }
    });

    this.values = values;
  }

  onMark(marker: number[]) {
    // console.log('Markers', this.markers);
  }
  onChange(marker: number[]) {
    // console.log('Marker', marker);
  }
  selectMarker(index: number) {
    this.imgMap.markerActive = index;
    this.imgMap.draw();
  }
  removeMarker(index: number) {
    this.service.markers.splice(index, 1);
    if (index === this.imgMap.markerActive) {
      this.imgMap.markerActive = null;
    } else if (index < this.imgMap.markerActive) {
      this.imgMap.markerActive--;
    }
    this.imgMap.draw();
  }
}
