// src/app/app.routes.ts
import { Routes } from '@angular/router';
import { AuthGuard } from './api/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  {
    path: 'register',
    loadComponent: () => import('./register/register.component').then(m => m.RegisterComponent)
  },
  {
    path: 'login',
    loadComponent: () => import('./login/login.component').then(m => m.LoginComponent)
  },

  {
    path: 'dashboard',
    loadComponent: () => import('./dashboard/dashboard.component').then(m => m.DashboardComponent),
    canActivate: [AuthGuard]
  },
  {
    path: 'orders',
    loadComponent: () => import('./order/order-list.component').then(m => m.OrderListComponent),
    canActivate: [AuthGuard]
  },
  {
    path: 'order/add',
    loadComponent: () => import('./order-add/order-add.component').then(m => m.OrderAddComponent),
    canActivate: [AuthGuard]
  },
  {
    path: 'order/edit/:id',
    loadComponent: () => import('./order-edit/order-edit.component').then(m => m.OrderEditComponent),
    canActivate: [AuthGuard]
  },
  {
    path: 'user',
    loadComponent: () => import('./user/user-list.component').then(m => m.UserListComponent),
    canActivate: [AuthGuard]
  },
  {
    path: 'user/add',
    loadComponent: () => import('./user/user-form.component').then(m => m.UserFormComponent),
    canActivate: [AuthGuard]
  },
  {
    path: 'user/edit/:id',
    loadComponent: () => import('./user/user-form.component').then(m => m.UserFormComponent),
    canActivate: [AuthGuard]
  },
  {
    path: '**',
    redirectTo: 'login'
  }
];
