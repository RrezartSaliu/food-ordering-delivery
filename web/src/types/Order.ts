export interface Order {
    id: number
    amount: number
    orderDateTime: string
    userId: number
    orderStatus: string
    restaurantId: number
    address: string
    latitude: number
    longitude: number
}