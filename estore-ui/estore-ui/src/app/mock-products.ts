import { Product } from './object-interfaces/product';

export const PRODUCTS: Product[] = [
    { id: 1, name: 'cRaZy BlEnD', type: 'coffee', modPrice: 0.44, ingredients: new Map<string, number>([["pinto", 0.40], ["black", 0.60]]) },
    { id: 2, name: 'Unabomber Special', type: 'coffee', modPrice: 0.99, ingredients: new Map<string, number>([["gunpowder", 1.00]]) }
];