import { useEffect } from "react"
import { useApi } from "../hooks/useApi"
import type { Order } from "../types/Order"
import { useAuth } from "../util/AuthProvider"
import { useWebSocket } from "../websocket/WebsocketProvider"

const UserOrdersPage = () => {
    const {token } = useAuth()
    const ordersApi = useApi<Order[]>(`${import.meta.env.VITE_API_URL}order/userOrders`, token)
    const { lastMessage} = useWebSocket()

    useEffect(()=>{
        ordersApi.get()
    },[])

    useEffect(() => {
        if (!lastMessage) return;

        if (lastMessage.type === "ORDER_DELIVERED") {
            ordersApi.setData(prev=>{
                    if(!prev?.data) return prev;
                    return {
                        ...prev,
                        data: prev.data.map(order=>order.id ===lastMessage.orderId?{...order, orderStatus: "DELIVERED"}:order)
                    }
                })
        }
    }, [lastMessage]);

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