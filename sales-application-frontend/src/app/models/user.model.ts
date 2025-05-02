export interface UserDTO {
    name: string;
    phone: string;
    email: string;
    password: string;
    address: string;
  }
  
  export interface UserResponseDTO {
    id: number;
    name: string;
    phone: string;
    email: string;
    address: string;
    password: string;
  }
  
  export interface AuthRequestDTO {
    email: string;
    password: string;
  }
  
  export interface AuthResponseDTO {
    token: string;
  }
  