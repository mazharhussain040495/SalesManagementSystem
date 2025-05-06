import { Component } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../api/auth.service';
import { Router, RouterModule } from '@angular/router';
import { AuthRequestDTO, AuthResponseDTO } from '../models/user.model'; 

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  successMessage = '';
  errorMessage = '';

  form;


  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    // Initialize form inside constructor after fb is available
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }


  submit() {
    if (this.form.valid) {

      const user: AuthRequestDTO = {
              email: this.form.value.email!,
              password: this.form.value.password!,
            };

      this.authService.login(user).subscribe({
        next: (res) => {
          this.authService.saveToken(res.token);
          this.router.navigate(['/dashboard']); // Or wherever you want
        },
        error: () => {
          this.errorMessage = 'Invalid credentials.';
        }
      });
    }
  }
}
