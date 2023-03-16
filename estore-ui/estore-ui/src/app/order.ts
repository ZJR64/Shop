export interface Order {
    id: number;
    email: string;
    address: string;
    payment : string;
    price : number;
    products: Map<String, number[]>;
    fulfilled : boolean;
    [key: string]: any;
  }