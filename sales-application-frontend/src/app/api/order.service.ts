// src/app/api/order.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { OrderRequestDTO, OrderResponseDTO } from '../models/order.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class OrderService {
  private baseUrl = 'http://localhost:8082/api/orders';

  constructor(private http: HttpClient) {}

  getAll(): Observable<OrderResponseDTO[]> {
    return this.http.get<OrderResponseDTO[]>(`${this.baseUrl}/`);
  }

  getById(id: number): Observable<OrderResponseDTO> {
    return this.http.get<OrderResponseDTO>(`${this.baseUrl}/${id}`);
  }

  getByCustomer(email: string): Observable<OrderResponseDTO[]> {
    return this.http.get<OrderResponseDTO[]>(`${this.baseUrl}/customer/${email}`);
  }

  create(order: OrderRequestDTO): Observable<OrderResponseDTO> {
    return this.http.post<OrderResponseDTO>(`${this.baseUrl}/`, order);
  }

  update(id: number, order: OrderRequestDTO): Observable<OrderResponseDTO> {
    return this.http.put<OrderResponseDTO>(`${this.baseUrl}/${id}`, order);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
