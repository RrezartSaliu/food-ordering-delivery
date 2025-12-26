import { useEffect } from "react"
import { useApi } from "../hooks/useApi"
import type { Order } from "../types/Order"
import { useAuth } from "../util/AuthProvider"

const DriverOrdersPage = () => {
    const {token} = useAuth()
    const driversRestaurantApi = useApi<number|null>(`${import.meta.env.VITE_API_URL}user/get-driver-restaurant`, token)
    const driverOrdersApi = useApi<Order[]|null>(`${import.meta.env.VITE_API_URL}order/waiting-orders-restaurant?restaurantId=`,token)

    useEffect(()=>{
        driversRestaurantApi.get().then(res=>{
            if (res){
                console.log(res.data)
                driverOrdersApi.get(`${res.data}`).then(res=>console.log(res)
                )
            }
        })
    },[])

    return (
        <div id="home-container">
            DriverOrders
        </div>
    )
}

export default DriverOrdersPage