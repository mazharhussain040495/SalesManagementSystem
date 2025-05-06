// src/app/order/order-list.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { OrderService } from '../api/order.service';
import { OrderResponseDTO } from '../models/order.model';
import { HeaderComponent } from '../shared/header/header.component';

@Component({
  selector: 'app-order-list',
  standalone: true,
  imports: [CommonModule, RouterModule, HeaderComponent],
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.scss']
})
export class OrderListComponent implements OnInit {
  orders: OrderResponseDTO[] = [];
  error = '';

  constructor(private orderService: OrderService, private router: Router) {}

  ngOnInit(): void {
    this.loadOrders();
  }

  loadOrders() {
    this.orderService.getAll().subscribe({
      next: (data) => {
        this.orders = data;
      },
      error: (err) => {
        this.error = 'Failed to load orders';
      }
    });
  }

  deleteOrder(id: number) {
    this.orderService.delete(id).subscribe({
      next: () => {
        this.orders = this.orders.filter(order => order.id !== id);
      },
      error: () => {
        this.error = 'Failed to delete order';
      }
    });
  }

  editOrder(id: number) {
    this.router.navigate(['/order/edit', id]);
  }

  addOrder() {
    this.router.navigate(['/order/add']);
  }

}
