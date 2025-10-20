import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ManagerRoutingModule } from './manager-routing-module';
import { Requestedloans } from './requestedloans/requestedloans';
import { Managerdashboard } from './managerdashboard/managerdashboard';
import { ManagerProfile } from './manager-profile/manager-profile';


@NgModule({
  declarations: [
    Requestedloans,
    Managerdashboard,
    ManagerProfile
  ],
  imports: [
    CommonModule,
    ManagerRoutingModule
  ]
})
export class ManagerModule { }
