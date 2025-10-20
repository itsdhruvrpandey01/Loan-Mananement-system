import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanActivateFn, GuardResult, MaybeAsync, Router, RouterStateSnapshot } from '@angular/router';
import { auth } from '../../../services/auth';

@Injectable({
  providedIn:'root'
})

export class AuthGuard implements CanActivate{

  constructor(
    private authService:auth,
    private router:Router
  ){}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    if(this.authService.isAuthenticated()){
      return true;
    }

    this.router.navigate(['/auth/login'])
    return false;
  }
}