import { useEffect, useState } from "react";
import { useApi } from "../hooks/useApi";
import type { Order } from "../types/Order";
import { useAuth } from "../util/AuthProvider";
import { useWebSocket } from "../websocket/WebsocketProvider";

const RestaurantOrdersPage = () => {
  const { token } = useAuth();
  const restaurantOrdersApi = useApi<Order[]>(
    `${import.meta.env.VITE_API_URL}order/restaurant-orders`,
    token
  );
  const getOrderCreatedApi = useApi<Order>(
    `${import.meta.env.VITE_API_URL}order/get-order?orderId=`,
    token
  );
  useEffect(() => {
    restaurantOrdersApi.get();
  }, [token]);

  const { lastMessage } = useWebSocket();
  const [newOrderToast, setNewOrderToast] = useState<string | null>(null);

  useEffect(() => {
    if (!lastMessage) return;

    if (lastMessage.type === "order-created") {
      console.log(lastMessage);
      
      getOrderCreatedApi.get(lastMessage.orderId).then((res) => {
        if (!res?.data) return;

        restaurantOrdersApi.setData((prev) => ({
          success: true,
          message: "Order received",
          data: prev ? [res.data, ...prev.data] : [res.data],
        }));

        setNewOrderToast("Client made order!");
        setTimeout(() => setNewOrderToast(null), 4000);
      });
    }
  }, [lastMessage]);

  return (
    <div id="home-container">
      <div>
        {restaurantOrdersApi.data?.data.map((restaurant) => (
          <div key={restaurant.id}>
            {restaurant.id}-{restaurant.orderDateTime}-{restaurant.orderStatus}-
            {restaurant.amount}-{restaurant.userId}
          </div>
        ))}
      </div>
      {newOrderToast && (
        <div className="toast success-toast">{newOrderToast}</div>
      )}
    </div>
  );
};

export default RestaurantOrdersPage;
