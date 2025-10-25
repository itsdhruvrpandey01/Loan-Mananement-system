import { Component, OnInit } from '@angular/core';
import { UserDetailsResponseDto } from '../../../entity/UserDetailsResponseDto';
import { user } from '../../../services/user';


@Component({
  selector: 'app-customerprofile',
  standalone: false,
  templateUrl: './customerprofile.html',
  styleUrl: './customerprofile.css'
})
export class Customerprofile implements OnInit {
  userProfile?: UserDetailsResponseDto;
  userId = localStorage.getItem('userId') || '';

  constructor(private userService: user) { }

  ngOnInit(): void {
    this.loadUserProfile();
  }

  loadUserProfile() {

    alert(this.userId)
    this.userService.getUserProfile(this.userId).subscribe({
      next: (data) => this.userProfile = data,
      error: (err) => console.error('Error loading user profile:', err)
    });
  }
}