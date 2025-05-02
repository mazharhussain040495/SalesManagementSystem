import { Component } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { OrderService } from '../api/order.service';
import { Router } from '@angular/router';
import { OrderRequestDTO } from '../models/order.model';

@Component({
  selector: 'app-order-add',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './order-add.component.html',
  styleUrls: ['./order-add.component.scss']
})
export class OrderAddComponent {
  successMessage = '';
  errorMessage = '';
  form;

  constructor(private fb: FormBuilder, private orderService: OrderService, private router: Router) {
    this.form = this.fb.group({
      productName: ['', Validators.required],
      quantity: [1, [Validators.required, Validators.min(1)]],
      price: [0, [Validators.required, Validators.min(0)]],
      customerEmail: ['', [Validators.required, Validators.email]],
    });

  }

  
  submit() {
    if (this.form.valid) {
      
      const order: OrderRequestDTO = {
        productName: this.form.value.productName!,
        quantity: this.form.value.quantity!,
        price: this.form.value.price!,
        customerEmail: this.form.value.customerEmail!,
        
        };
      this.orderService.create(order).subscribe({
        next: () => {
          this.successMessage = 'Order added successfully';
          this.router.navigate(['/orders']);
        },
        error: () => {
          this.errorMessage = 'Failed to add order';
        }
      });
    }
  }
}
