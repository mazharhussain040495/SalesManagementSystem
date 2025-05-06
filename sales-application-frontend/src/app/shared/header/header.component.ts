import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../api/auth.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  constructor(
    private router: Router,
    private location: Location,
    private authService: AuthService
  ) {}

  goBack() {
    this.location.back();
  }

  goToDashboard() {
    this.router.navigate(['/dashboard']); // Change as needed
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
