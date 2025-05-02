// src/app/app.routes.ts
import { Routes } from '@angular/router';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './api/auth.guard';
import { OrderListComponent } from './order/order-list.component';
import { OrderEditComponent } from './order-edit/order-edit.component';
import { OrderAddComponent } from './order-add/order-add.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'orders', component: OrderListComponent },
  { path: 'order/add', component: OrderAddComponent },
  { path: 'order/edit/:id', component: OrderEditComponent },
];
