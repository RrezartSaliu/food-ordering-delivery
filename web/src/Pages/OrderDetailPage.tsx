import { useParams } from "react-router-dom"
import { useApi } from "../hooks/useApi"
import { useAuth } from "../util/AuthProvider"
import type { Order } from "../types/Order"
import { useEffect } from "react"
import Map from "../components/Map"

const OrderDetailPage = () => {
    const { id } = useParams()
    const {token, role} = useAuth()
    const getOrderApi = useApi<Order|null>(`${import.meta.env.VITE_API_URL}order/get-order?orderId=`, token)


    useEffect(()=>{
        getOrderApi.get(id)

    },[])

    return (
        <div id="home-container">
            OrderDetailPage
            <p>{getOrderApi.data?.data?.id}</p>
            <p>{getOrderApi.data?.data?.amount}</p>
            <p>{getOrderApi.data?.data?.orderDateTime}</p>
            <p>{getOrderApi.data?.data?.restaurantId}</p>
            <p>{getOrderApi.data?.data?.orderStatus}</p>
            <p>{getOrderApi.data?.data?.address}</p>
            { role && role === "ROLE_USER" && getOrderApi.data?.data &&
                <Map coords={[getOrderApi.data.data.latitude, getOrderApi.data.data.longitude]}></Map>
            }
        </div>
    )
}

export default OrderDetailPage