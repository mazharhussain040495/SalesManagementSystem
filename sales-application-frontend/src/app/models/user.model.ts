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
    email: string;
    password: string;
    phone: string;
    address: string;
    
  }
  
  export interface AuthRequestDTO {
    email: string;
    password: string;
  }
  
  export interface AuthResponseDTO {
    token: string;
  }
  