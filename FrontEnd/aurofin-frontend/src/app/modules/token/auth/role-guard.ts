import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, GuardResult, MaybeAsync, Router, RouterStateSnapshot } from '@angular/router';
import { auth } from '../../../services/auth';

@Injectable({
  providedIn: "root"
})

export class RoleGuard implements CanActivate {
  constructor(
    private authService: auth,
    private router: Router
  ) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    const expectedRoles = route.data['roles'] as Array<string>;
    const currentUserRole = this.authService.getUserRole();

    

    if(!expectedRoles || expectedRoles.length==0){
      return true;
    }

    if(currentUserRole && this.authService.isAuthenticated() && expectedRoles.includes(currentUserRole)){
      return true;
    }

    this.router.navigate(['unauthorized'])
    return false;
  }
}