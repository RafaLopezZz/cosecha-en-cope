export interface LoginRequest { email: string; password: string; }
export interface UsuarioRequest {
  email: string;
  password: string;
  nombre?: string;
  direccion?: string;
  telefono?: string;
}
export interface JwtResponse {
  token: string;
  id: number;
  email: string;
  tipoUsuario: 'CLIENTE' | 'PRODUCTOR';
  roles: string[];
}
export interface UsuarioResponse { idUsuario: number; email: string; tipoUsuario: string; rol: string; }
export interface ApiError { code?: number; message: string; }
