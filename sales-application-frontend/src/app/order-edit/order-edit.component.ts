import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { OrderService } from '../api/order.service';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderRequestDTO } from '../models/order.model';
import { HeaderComponent } from "../shared/header/header.component";

@Component({
  selector: 'app-order-edit',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, HeaderComponent],
  templateUrl: './order-edit.component.html',
  styleUrls: ['./order-edit.component.scss']
})
export class OrderEditComponent implements OnInit {
  successMessage = '';
  errorMessage = '';
  form;
  orderId!: number;
  

  constructor(private fb: FormBuilder, private orderService: OrderService, private router: Router, private route: ActivatedRoute) {
    this.form = this.fb.group({
      productName: ['', Validators.required],
      quantity: [1, [Validators.required, Validators.min(1)]],
      price: [0, [Validators.required, Validators.min(0)]],
      customerEmail: ['', [Validators.required, Validators.email]],
    });
  }

  ngOnInit(): void {
    this.orderId = +this.route.snapshot.paramMap.get('id')!;
    this.loadOrder();
  }

  loadOrder() {
    this.orderService.getById(this.orderId).subscribe({
      next: (order) => {
        this.form.patchValue(order);
      },
      error: () => {
        this.errorMessage = 'Failed to load order';
      }
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
      this.orderService.update(this.orderId, order).subscribe({
        next: () => {
          this.successMessage = 'Order updated successfully';
          this.router.navigate(['/orders']);
        },
        error: () => {
          this.errorMessage = 'Failed to update order';
        }
      });
    }
  }
}
