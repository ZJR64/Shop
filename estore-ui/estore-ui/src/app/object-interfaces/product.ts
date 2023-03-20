export interface Product {
    id: number;
    name: string;
    type: string;
    modPrice: number;
    ingredients: Map<String, number>;
    [key: string]: any;
}