export interface Order {
    id: number;
    email: string;
    address: string;
    payment : string;
    price : number;
    products: {
        [key: string]: number[];
      };
    fulfilled : boolean;
    [key: string]: any;
  }

