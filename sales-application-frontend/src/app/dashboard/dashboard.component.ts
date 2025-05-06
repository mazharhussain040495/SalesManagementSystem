// src/app/dashboard/dashboard.component.ts
import { Component } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {
  constructor(private router: Router, private location: Location) {}

  goTo(path: string) {
    this.router.navigate([path]);
  }

  logout() {
    localStorage.removeItem('jwt');
    this.router.navigate(['/login']);
  }

  goBack() {
    this.location.back();
  }
}
