import { Component } from '@angular/core';
import { user } from '../../../services/user';


@Component({
  selector: 'app-managerdashboard',
  standalone: false,
  templateUrl: './managerdashboard.html',
  styleUrl: './managerdashboard.css'
})
export class Managerdashboard {
userId = localStorage.getItem('userId') || '';
constructor(private userService:user){

}
  ngOnInit(){
    console.log("user id")
    console.log(this.userId);
  this.userService.getManagerID(this.userId).subscribe({
      next: (managerId) => {
        console.log('Customer ID:', managerId);
        localStorage.setItem('managerId', managerId);
      },
      error: (err) => {
        console.error('Error fetching customer ID:', err);
      }
    });
}
}
