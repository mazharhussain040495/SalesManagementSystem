// src/app/user/user-list.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { UserService } from '../api/user.service';
import { UserResponseDTO } from '../models/user.model';
import { HeaderComponent } from '../shared/header/header.component';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [CommonModule, RouterModule, HeaderComponent],
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {
  users: UserResponseDTO[] = [];
  error = '';

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers() {
    this.userService.getAll().subscribe({
      next: (data) => this.users = data,
      error: () => this.error = 'Failed to load users'
    });
  }

  editUser(id: number) {
    this.router.navigate(['/user/edit', id]);
  }

  addUser() {
    this.router.navigate(['/user/add']);
  }

  deleteUser(id: number) {
    this.userService.delete(id).subscribe({
      next: () => this.users = this.users.filter(u => u.id !== id),
      error: () => this.error = 'Failed to delete user'
    });
  }
}
