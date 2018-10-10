import {Location} from '../app.service';
import {Component, Input, OnDestroy} from '@angular/core';

@Component({
  selector: 'user-data-popup',
  templateUrl: 'user-data-popup.html',
  styleUrls: ['../co-ordinates-pop/co-ordinates-pop.css', 'user-data-popup.css']
})
export class UserDataPopupComponent implements OnDestroy {

  @Input()
  userDataPop: Location;

  @Input()
  imgageUrl: string = '/emoji.png';

  ngOnDestroy(): void {
    console.log("destroyed");
    this.imgageUrl = '/emoji.png';
  }

}
