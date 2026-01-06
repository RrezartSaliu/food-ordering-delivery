import { useParams } from "react-router-dom";
import { useApi } from "../hooks/useApi";
import { useAuth } from "../util/AuthProvider";
import type { Order } from "../types/Order";
import { useEffect, useState } from "react";
import Map from "../components/Map";
import { useWebSocket } from "../websocket/WebsocketProvider";
import type { LatLngExpression } from "leaflet";
import type { Toast } from "../types/Toast";

const OrderDetailPage = () => {
  const { id } = useParams();
  const { token, role } = useAuth();
  const getOrderApi = useApi<Order | null>(
    `${import.meta.env.VITE_API_URL}order/get-order?orderId=`,
    token
  );
  const { lastMessage } = useWebSocket();
  const [driverPos, setDriverPos] = useState<LatLngExpression | null>(null);
  const [ orderDeliveredToast, setOrderDeliveredToast] = useState<Toast|null>()

  useEffect(() => {
    getOrderApi.get(id);
  }, []);

  useEffect(() => {
    if (!lastMessage) return;

    if (lastMessage.type === "DRIVER_LOCATION" && lastMessage.orderId == id) {
      setDriverPos([lastMessage.lat, lastMessage.lng]);
    }

    if (lastMessage.type === "ORDER_DELIVERED" && lastMessage.orderId == id) {
      getOrderApi.setData((prev) => {
        if (!prev || !prev.data) return prev;

        return {
          ...prev,
          data: {
            ...prev.data,
            orderStatus: "DELIVERED",
          },
        };
      });
      setOrderDeliveredToast({message: "Order delivered!", success: true})

      setTimeout(()=>{
        setOrderDeliveredToast(null)
      }, 4000)
    }
  }, [lastMessage]);

  return (
    <div id="home-container">
      OrderDetailPage
      <p>{getOrderApi.data?.data?.id}</p>
      <p>{getOrderApi.data?.data?.amount}</p>
      <p>{getOrderApi.data?.data?.orderDateTime}</p>
      <p>{getOrderApi.data?.data?.restaurantId}</p>
      <p>{getOrderApi.data?.data?.orderStatus}</p>
      <p>{getOrderApi.data?.data?.address}</p>
      {role &&
        role === "ROLE_USER" &&
        getOrderApi.data?.data &&
        getOrderApi.data.data.orderStatus === "DELIVERING" && (
          <Map
            coords={[
              getOrderApi.data.data.latitude,
              getOrderApi.data.data.longitude,
            ]}
            driverCoords={driverPos}
          ></Map>
        )}
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
  );
};

export default OrderDetailPage;
