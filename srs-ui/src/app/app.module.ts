import {AdminPopComponent} from './admin-pop/admin-pop';
import {NgModule} from '@angular/core';
import {HttpModule} from '@angular/http';
import {FormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';
import {
  DxPopupModule, DxTemplateModule, DxFormModule, DxButtonModule, DxMenuModule,
  DxBarGaugeModule, DxCheckBoxModule, DxSwitchModule
} from 'devextreme-angular';

import {AppComponent} from './app.component';
import {Service} from './app.service';
import {CoOrdinatesPopComponent} from './co-ordinates-pop/co-ordinates-pop';
import {FooterComponent} from './footer/footer.component';
import {HeaderComponent} from './header/header.component';
import {ImgMapComponent} from './ng2-img-map';
import {UserDataPopupComponent} from './user-data-popup/user-data-popup';
import {ImageZoomModule} from 'angular2-image-zoom';

@NgModule({
  imports: [
    BrowserModule, HttpModule, FormsModule,
    DxPopupModule, DxTemplateModule, DxFormModule, DxButtonModule, DxMenuModule,
    DxBarGaugeModule, DxCheckBoxModule, DxSwitchModule, ImageZoomModule
  ],
  declarations: [
    AppComponent, ImgMapComponent, CoOrdinatesPopComponent, AdminPopComponent, UserDataPopupComponent,
    HeaderComponent, FooterComponent
  ],
  providers: [Service],
  bootstrap: [AppComponent]
})
export class AppModule {}
