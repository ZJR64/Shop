export interface User {
  id: number;
  email: string;
  name: string;
  password: string;
  address: string;
  admin: boolean;
  payInfo: string[];
  cart: Map<number, String[]>;
}