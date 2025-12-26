import { useEffect } from "react"
import { useApi } from "../hooks/useApi"
import type { Order } from "../types/Order"
import { useAuth } from "../util/AuthProvider"

const UserOrdersPage = () => {
    const {token } = useAuth()
    const ordersApi = useApi<Order[]>(`${import.meta.env.VITE_API_URL}order/userOrders`, token)

    useEffect(()=>{
        ordersApi.get()
    },[])

    return (
        <div id="home-container">
            {ordersApi.data?.data.map(order=>(
                <div>
                    {order.id} - 
                    total: {order.amount} - 
                    datetime: {order.orderDateTime} -
                    status: {order.orderStatus} -
                    user: {order.userId}
                </div>
            ))}
        </div>
    )
}

export default UserOrdersPage