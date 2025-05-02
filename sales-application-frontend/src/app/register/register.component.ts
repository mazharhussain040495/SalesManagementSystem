import { Component } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../api/auth.service'; 
import { Router, RouterModule } from '@angular/router';
import { UserDTO } from '../models/user.model';  // Import UserDTO from models

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent {
  successMessage = '';
  errorMessage = '';

  form;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    // Initialize form inside constructor after fb is available
    this.form = this.fb.group({
      name: ['', Validators.required],
      phone: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      address: ['', Validators.required],
    });
  }

  submit() {
    if (this.form.valid) {
      // Map the form value to the UserDTO
      const user: UserDTO = {
        name: this.form.value.name!,
        phone: this.form.value.phone!,
        email: this.form.value.email!,
        password: this.form.value.password!,
        address: this.form.value.address!,
      };

      // Call the authService register method
      this.authService.register(user).subscribe({
        next: (res) => {
          this.successMessage = 'User registered successfully!';
          this.errorMessage = '';
          this.form.reset();
        },
        error: (err) => {
          this.errorMessage = 'Registration failed. Please try again.';
          this.successMessage = '';
        }
      });
    }
  }
}
