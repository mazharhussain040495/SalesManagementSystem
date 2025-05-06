// src/app/user/user-form.component.ts
import { Component, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { UserService } from '../api/user.service';
import { UserDTO } from '../models/user.model';
import { HeaderComponent } from '../shared/header/header.component';

@Component({
  selector: 'app-user-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule,HeaderComponent],
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.scss']
})
export class UserFormComponent implements OnInit {

  form;
  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required], // Only used when adding
      phone: ['', Validators.required],
      address: ['', Validators.required],
    });
  }

  

  isEditMode = false;
  userId!: number;

  

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.isEditMode = true;
      this.userId = +idParam;
      this.userService.getById(this.userId).subscribe(user => {
        this.form.patchValue({
          name: user.name,
          email: user.email,
          password: user.password,
          phone: user.phone,
          address: user.address,

        });
      });
    }
  }

  submit() {
    if (this.form.valid) {
    const user: UserDTO = {
        name: this.form.value.name!,
        email: this.form.value.email!,
        password: this.form.value.password!,
        phone: this.form.value.phone!,
        address: this.form.value.address!
    }


    if (this.isEditMode) {
      this.userService.update(this.userId, user).subscribe(() => {
        this.router.navigate(['/user']);
      });
    } else {
      this.userService.create(user).subscribe(() => {
        this.router.navigate(['/user']);
        });
      }
    }
  }

  cancel() {
    this.router.navigate(['/user']);
  }
}
