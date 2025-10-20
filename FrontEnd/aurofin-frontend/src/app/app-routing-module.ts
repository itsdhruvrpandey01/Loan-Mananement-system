import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'admin', loadChildren: () => {
      return import('./modules/admin/admin-module').then(m => m.AdminModule)
    },

  },
  {
    path: 'customer', loadChildren: () => {
      return import('./modules/customer/customer-module').then(m => m.CustomerModule)
    }
  },
  {
    path: 'manager', loadChildren: () => {
      return import('./modules/manager/manager-module').then(m => m.ManagerModule)
    }
  },
  {
    path: 'auth', loadChildren: () => {
      return import('./modules/auth/auth-module').then(m => m.AuthModule)
    }
  }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
