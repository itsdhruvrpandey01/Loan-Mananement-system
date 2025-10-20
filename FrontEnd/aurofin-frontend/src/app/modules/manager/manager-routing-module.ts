import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Managerdashboard } from './managerdashboard/managerdashboard';
import { Requestedloans } from './requestedloans/requestedloans';
import { ManagerProfile } from './manager-profile/manager-profile';

const routes: Routes = [
  {
    path: '', component: Managerdashboard,
    children: [
      { path: 'requested-loans', component: Requestedloans },
      { path: 'view-profile', component: ManagerProfile },

    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ManagerRoutingModule { }
