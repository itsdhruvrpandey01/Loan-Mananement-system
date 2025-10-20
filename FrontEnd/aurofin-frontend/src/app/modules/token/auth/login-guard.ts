import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanActivateFn, GuardResult, MaybeAsync, Router, RouterStateSnapshot } from '@angular/router';
import { auth } from '../../../services/auth';

@Injectable({
  providedIn: 'root'
})

export class LoginGuard implements CanActivate {
  constructor(
    private authService: auth,
    private router: Router
  ) { };
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    console.log("start")
    if (this.authService.isAuthenticated()) {
      const userRole = this.authService.getUserRole();

      const dashboardRoute = userRole === 'ROLE_ADMIN' ? "admin" : userRole === "ROLE_MANAGER" ? "officer" : "customer";
      this.router.navigate([dashboardRoute])
      return false;
    }
    return true;
  }
}