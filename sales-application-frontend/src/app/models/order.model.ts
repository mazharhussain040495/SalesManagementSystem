// src/app/models/order.model.ts
export interface OrderRequestDTO {
    productName: string;
    quantity: number;
    price: number;
    customerEmail: string;
  }
  
  export interface OrderResponseDTO {
    id: number;
    productName: string;
    quantity: number;
    price: number;
    orderDate: string;
    customerEmail: string;
  }
  