import { Component } from '@angular/core';
import { user } from '../../../services/user';

@Component({
  selector: 'app-customerdashboard',
  standalone: false,
  templateUrl: './customerdashboard.html',
  styleUrl: './customerdashboard.css'
})
export class Customerdashboard {
  userId = localStorage.getItem('userId') || '';
constructor(private userService:user){

}
  ngOnInit(){
  this.userService.getCustomerIdByUserId(this.userId).subscribe({
      next: (customerId) => {
        console.log('Customer ID:', customerId);
        localStorage.setItem('customerId', customerId);
      },
      error: (err) => {
        console.error('Error fetching customer ID:', err);
      }
    });
}
}
