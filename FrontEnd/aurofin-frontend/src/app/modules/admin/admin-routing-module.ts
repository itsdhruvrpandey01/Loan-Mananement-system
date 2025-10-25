import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Admindashboard } from './admindashboard/admindashboard';
import { Addmanager } from './addmanager/addmanager';
import { Collateral } from './collateral/collateral';
import { Loantype } from './loantype/loantype';
import { Loanscheme } from './loanscheme/loanscheme';
import { ListEmployees } from './list-employees/list-employees';
import { ListLoans } from './list-loans/list-loans';
import { Vieweditloanscheme } from './vieweditloanscheme/vieweditloanscheme';

const routes: Routes = [
  {
    path: '', component: Admindashboard,
    children: [
      { path: 'add-manager', component: Addmanager },
      { path: 'collaterals', component: Collateral },
      { path: 'loan-types', component: Loantype },
      {path:'list-employees',component:ListEmployees},
      { path: 'loan-schemes', component: ListLoans }, // List of loans
      { path: 'loan-scheme', component: Loanscheme }, // Create new loan
      { path: 'loan-scheme/:id', component: Vieweditloanscheme } // View / Edit existing loan
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
