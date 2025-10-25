import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing-module';
import { Admindashboard } from './admindashboard/admindashboard';
import { Loanscheme } from './loanscheme/loanscheme';
import { Addmanager } from './addmanager/addmanager';
import { Collateral } from './collateral/collateral';
import { Loantype } from './loantype/loantype';
import { ReactiveFormsModule } from '@angular/forms';
import { ListEmployees } from './list-employees/list-employees';
import { ListLoans } from './list-loans/list-loans';
import { Vieweditloanscheme } from './vieweditloanscheme/vieweditloanscheme';


@NgModule({
  declarations: [
    Admindashboard,
    Loanscheme,
    Addmanager,
    Collateral,
    Loantype,
    ListEmployees,
    ListLoans,
    Vieweditloanscheme
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    ReactiveFormsModule
  ]
})
export class AdminModule { }
