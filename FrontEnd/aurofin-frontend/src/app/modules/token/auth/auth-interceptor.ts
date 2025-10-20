import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { auth } from '../../../services/auth';
import { Observable } from 'rxjs';


@Injectable()
export class AuthInterceptor implements HttpInterceptor{
  constructor(private authService:auth){}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const authToken = this.authService.getToken();

    if(authToken){
      const authReq = req.clone({
        setHeaders:{
          Authorization:`Bearer ${authToken}`
        }
      })
      return next.handle(authReq)
    }

    return next.handle(req)
  }
}