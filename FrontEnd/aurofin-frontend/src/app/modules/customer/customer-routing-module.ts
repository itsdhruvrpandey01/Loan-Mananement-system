import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Customerdashboard } from './customerdashboard/customerdashboard';
import { Appliedloans } from './appliedloans/appliedloans';
import { Applyforloan } from './applyforloan/applyforloan';
import { Customerprofile } from './customerprofile/customerprofile';
import { Payment } from './payment/payment';
import { ViewPayments } from './view-payments/view-payments';
import { Viewloanschemes } from './viewloanschemes/viewloanschemes';
import { Listofloans } from './listofloans/listofloans';

const routes: Routes = [
  {
    path: '', component: Customerdashboard,
    children: [
      { path: 'applied-loans', component: Appliedloans },
      { path: 'apply-for-loan', component: Applyforloan },
      { path: 'customer-profile', component: Customerprofile },
      { path: 'payment', component: Payment },
      { path: 'view-payments', component: ViewPayments },
      { path: 'view-loan-schemes', component: Viewloanschemes },
      { path: 'list-of-loans', component: Listofloans }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomerRoutingModule { }
