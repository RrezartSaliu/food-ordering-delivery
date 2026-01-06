import { useEffect, useState } from "react"
import { useApi } from "../hooks/useApi"
import type { Order } from "../types/Order"
import { useAuth } from "../util/AuthProvider"
import type { Toast } from "../types/Toast"
import { useCart } from "../util/CartContext"
import { useDriverDelivery } from "../util/DriverDeliveryContext"

const DriverCartPage = () =>{
    const { token } = useAuth()
    const driverCartApi = useApi<Order[]|null>(`${import.meta.env.VITE_API_URL}order/driver-cart`, token)
    const deliveredOrderApi = useApi<Order |null>(`${import.meta.env.VITE_API_URL}order/order-delivered?`, token)
    const [ orderDeliveredToast, setOrderDeliveredToast ] = useState<Toast |null>(null)
    const {driverCartCount,setDriverCartCount} = useCart()
    const { stopDelivery } = useDriverDelivery()

    useEffect(()=>{
        driverCartApi.get()
    },[])


    const delivered = async (orderId: number, restaurantId: number)=> {
        const res = await deliveredOrderApi.post(`orderId=${orderId}&restaurantId=${restaurantId}`,{})

        if(res){
            if(res.success){
                driverCartApi.setData(prev=>{
                    if(!prev?.data) return prev;
                    return {
                        ...prev,
                        data: prev.data.filter(order => order.id !== orderId)
                    }
                })
            }
            setOrderDeliveredToast({message: res.message, success: res.success})
            setDriverCartCount(driverCartCount-1)
            stopDelivery(orderId)
            setTimeout(() => {
                setOrderDeliveredToast(null);
            }, 4000);
        }
    }

    return (
        <div id="home-container">
            Driver cart
            {driverCartApi.data?.data?.map(order=>(
                <div key={order.id}>
                    <p>{order.id}</p>
                    <p>{order.amount}</p>
                    <p>{order.orderDateTime}</p>
                    <p>{order.orderStatus}</p>
                    <p>{order.restaurantId}</p>
                    <p>{order.userId}</p>
                    <button onClick={()=>delivered(order.id, order.restaurantId)}>delivered</button>
                    <hr></hr>
                </div>
            ))}
            {orderDeliveredToast && (
                <div
                className={`toast ${
                    orderDeliveredToast.success ? "success-toast" : "fail-toast"
                }`}
                >
                {orderDeliveredToast.message}
                </div>
            )}
        </div>
    )
}

export default DriverCartPage