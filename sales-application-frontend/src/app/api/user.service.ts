// src/app/api/user.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserDTO, UserResponseDTO } from '../models/user.model';

@Injectable({ providedIn: 'root' })
export class UserService {
  private baseUrl = 'http://localhost:8083/api/users';

  constructor(private http: HttpClient) {}

  getAll(): Observable<UserResponseDTO[]> {
    return this.http.get<UserResponseDTO[]>(`${this.baseUrl}/`);
  }

  getById(id: number): Observable<UserResponseDTO> {
    return this.http.get<UserResponseDTO>(`${this.baseUrl}/${id}`);
  }

  create(user: UserDTO): Observable<UserResponseDTO> {
    return this.http.post<UserResponseDTO>(`${this.baseUrl}/`, user);
  }

  update(id: number, user: UserDTO): Observable<UserResponseDTO> {
    return this.http.put<UserResponseDTO>(`${this.baseUrl}/${id}`, user);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
