import {Injectable} from '@angular/core';
import {Http, Headers, RequestOptions} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {isNullOrUndefined} from 'util';
import 'rxjs/add/operator/map';
import notify from 'devextreme/ui/notify';

export class Location {
  deskNo: string;
  latLong: number[];
  buildingName: string;
  floor: string;
  ownerGroup: string;
  displayName: string;
  prid: string;
  status: string;
  occupiedAt: any;
  validTill: any;
}

export class MetricsProduct {
  name: string;
  count: number;
  active: boolean;
}

export class UserService {
  userPrid: string;
  userName: string;
  admin: boolean;
}

export class Product {
  name?: string;
  buildingName?: string;
  floor?: string;
  iconSrc?: string;
  items?: Product[];
}

export class LocationMap {
  lattitude: number[];
  location: string;
}

let locationMap: LocationMap[] = [
  {
    lattitude: [60.98457016899339, 79.83367983367984],
    location: 'L11 WS 120'
  },
  {
    lattitude: [61.386861313868614, 81.01135190918473],
    location: 'L11 WS 119'
  },
  {
    lattitude: [62.48175182481752, 78.12177502579979],
    location: 'L11 WS 122'
  },
  {
    lattitude: [62.99270072992701, 79.46336429308566],
    location: 'L11 WS 121'
  }
];

let products: Product[] = [
  {
    name: 'TRIL - Neville',
    items: [{
      name: 'Floor - 10',
      buildingName: 'TRIL-NVL',
      floor: 'F10',
      iconSrc: '/Nvl-10th-floor.png',
    }, {
      name: 'Floor - 11',
      buildingName: 'TRIL-NVL',
      floor: 'F11',
      iconSrc: '/Nvl-11th-floor.png'
    }
    ]
  },
  {
    name: 'TRIL - Hardy',
    items: [{
      name: 'Floor - 2',
      buildingName: 'TRIL-HDY',
      floor: 'F2',
      iconSrc: '/Hdy-2nd-floor.png',
    }
    ]
  },
  {
    name: 'Metrics',
    buildingName: 'Metrics'
  }
];

@Injectable()
export class Service {
  closeOnOutsideClick: boolean = false;
  markers: number[][] = [];
  locationList: Location[] = [new Location()];
  userList: Location[] = [];
  admin: boolean = false;
  user: string;
  buildingName: string = 'TRIL-NVL';
  floor: string = 'F11';
  occupyResponse: string;
  userService: UserService = new UserService();
  currentImageSrc: string;
  metrics: boolean;
  showRegister: boolean = false;
  myLocation: Location = new Location();
  hOccupied: boolean = true;
  hUnOccupied: boolean = false;

  metricProducts: MetricsProduct[] = [{
    name: 'UnOccupied',
    count: 41,
    active: true
  }, {
    name: 'Occupied',
    count: 32,
    active: true
  }, {
    name: 'Registered',
    count: 13,
    active: true
  }, {
    name: 'Not - Registered',
    count: 48,
    active: true
  }, {
    name: 'Common Area',
    count: 24,
    active: true
  }];

  private xsrfToken = {
    'parameterName': '_csrf',
    'token': '',
    'headerName': 'X-CSRF-TOKEN'
  };

  constructor(private http: Http) {
    this.currentImageSrc = '/Nvl-11th-floor.png';
    this.metrics = false;
  }

  getUserService() {
    this.http.get('/user-service')
      .map(res => res.json())
      .subscribe(res => {
        this.userService = res;
        this.showRegister = res.admin;
        if (res.admin) {
          this.hUnOccupied = true;
        }
      },
      err => this.handleError(err));
  }

  getXsrfToken(): void {
    this.http.get('/csrf')
      .map(res => res.json())
      .subscribe(res => {
        this.xsrfToken = res;
      },
      err => this.handleError(err)
      );
  }

  private getRequestOptions() {
    const headers = new Headers({'Content-Type': 'application/json', 'X-CSRF-TOKEN': this.xsrfToken.token});
    return new RequestOptions({headers: headers});
  };

  showSelectedCheckbox(): void {
    this.markers = [];
    this.userList = [];
    if (this.hOccupied) {
      this.locationList.forEach((map) => {
        if (map.status === 'OCCUPIED') {
          this.markers.push(map.latLong);
          this.userList.push(map);
        }
      });
    }

    if (this.hUnOccupied) {
      this.locationList.forEach((map) => {
        if (map.status === 'FREE') {
          this.markers.push(map.latLong);
          this.userList.push(map);
        }
      });
    }
  }

  getProducts(): Product[] {
    return products;
  };

  occupyLocation(deskNo: string) {
    this.http.post('/location-occupy', deskNo, this.getRequestOptions())
      .map(res => res.text())
      .subscribe(
      res => {
        this.getAllLocationList('O');
        notify(res, 'success', 600);
      },
      err => this.handleError(err));
  };

  getLocation(lattitude: number[]): Location {
    let result = new Location();
    this.locationList.forEach((list) => {
      if (list.latLong[0] === lattitude[0]
        && list.latLong[1] === lattitude[1]) {
        result = list;
        return result;
      }
    });
    return result;
  };

  registerLocation(arg: Location) {
    arg.buildingName = this.buildingName;
    arg.floor = this.floor;
    let body = JSON.stringify(arg);
    this.http.post('/location-register', body, this.getRequestOptions())
      .map(res => res.text())
      .subscribe(
      res => {
        notify(res, 'success', 600);
        this.getAllLocationList('R');
      },
      err => this.handleError(err));
  };

  mySeatDetails(): Location {
    let location = new Location();
    this.locationList.forEach((map) => {
      if ((map.prid === this.userService.userPrid)
        && map.status === 'OCCUPIED') {
        location = map;
        this.myLocation = map;
      }
    });
    return location;
  }

  renderMarkers(arg: string) {
    if (arg === 'R') {
      this.hUnOccupied = true;
      this.hOccupied = false;
    } else {
      this.hOccupied = true;
      this.hUnOccupied = false;
    }
    this.showSelectedCheckbox();
    this.mySeatDetails();
  }

  getAllLocationList(arg: string) {
    this.http.get('/location-list')
      .map(res => res.json())
      .subscribe(res => {
        if (!isNullOrUndefined(res)) {
          this.locationList = res;
        }
        this.renderMarkers(arg);
      },
      err => this.handleError(err));
  };

  handleError(err: any) {
    notify('Server Error!', 'error', 600);
    return Observable.throw('Server error');
  };

  seatAlreadyBooked(): boolean {
    let result = false;
    if ((this.myLocation.status === 'OCCUPIED')
      || (this.mySeatDetails().status === 'OCCUPIED')) {
      notify('Seat already booked!', 'error', 600);
      result = true;
    }
    return result;
  }
}
