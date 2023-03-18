export interface User {
  id: number;
  email: string;
  name: string;
  password: string;
  address: string;
  admin: boolean;
  payInfo: string[];
  [key: string]: any;
  cart: Map<String, number[]>;
}

