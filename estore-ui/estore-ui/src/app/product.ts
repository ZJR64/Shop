export interface Product {
    id: number;
    name: string;
    type: string;
    modPrice: number;
    ingredients: Map<string, number>;
}