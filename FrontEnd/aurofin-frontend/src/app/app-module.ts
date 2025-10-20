import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Header } from './layout/header/header';
import { Footer } from './layout/footer/footer';
import { Siderbar } from './layout/siderbar/siderbar';

import { AuthInterceptor } from '../app/modules/token/auth/auth-interceptor';
import { AuthGuard } from '../app/modules/token/auth/auth-guard';
import { RoleGuard } from '../app/modules/token/auth/role-guard';
import { ListEmployees } from './modules/list-employees/list-employees';

@NgModule({
  declarations: [
    App,
    Header,
    Footer,
    Siderbar,
    ListEmployees
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [
    provideBrowserGlobalErrorListeners(),
    AuthGuard,
    RoleGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [App]
})
export class AppModule { }
