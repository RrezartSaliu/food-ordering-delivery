import type { Product } from "./Product"

export interface CartItem {
    id: number
    menuItemSnapshot: Product
    quantity: number
}