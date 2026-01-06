import { useEffect, useState } from "react";
import { useApi } from "../hooks/useApi";
import type { Order } from "../types/Order";
import { useAuth } from "../util/AuthProvider";
import type { Toast } from "../types/Toast";
import { useCart } from "../util/CartContext";
import { useWebSocket } from "../websocket/WebsocketProvider";
import { useDriverDelivery } from "../util/DriverDeliveryContext";

const DriverOrdersPage = () => {
  const { token } = useAuth();
  const {startDelivery} = useDriverDelivery()
  const driversRestaurantApi = useApi<number | null>(
    `${import.meta.env.VITE_API_URL}user/get-driver-restaurant`,
    token
  );
  const driverOrdersApi = useApi<Order[]>(
    `${
      import.meta.env.VITE_API_URL
    }order/waiting-orders-restaurant?restaurantId=`,
    token
  );
  const deliverOrderApi = useApi<Order | null>(
    `${import.meta.env.VITE_API_URL}order/deliver-order?`,
    token
  );
  const [deliverToast, setDeliverToast] = useState<Toast | null>(null);
  const { driverCartCount, setDriverCartCount } = useCart();
  const { lastMessage } = useWebSocket();
  const getOrderCreatedApi = useApi<Order>(
    `${import.meta.env.VITE_API_URL}order/get-order?orderId=`,
    token
  );
  const [newOrderToast, setNewOrderToast] = useState<string | null>(null);

  useEffect(() => {
    driversRestaurantApi.get().then((res) => {
      if (res) {
        console.log(res.data);
        driverOrdersApi.get(`${res.data}`).then((res) => console.log(res));
      }
    });
  }, []);

  useEffect(() => {
    if (!lastMessage) return;

    if (lastMessage.type === "order-created") {
      console.log(lastMessage);
      getOrderCreatedApi.get(lastMessage.orderId).then((res) => {
        if (!res?.data) return;

        driverOrdersApi.setData((prev) => ({
          success: true,
          message: "Order received",
          data: prev ? [res.data, ...prev.data] : [res.data],
        }));
      });
      setNewOrderToast("New order arrived!");
      setTimeout(() => setNewOrderToast(null), 4000);
    }
  }, [lastMessage]);

  const deliverOrder = async (orderId: number, restaurantId: number) => {
    const res = await deliverOrderApi.post(
      `orderId=${orderId}&restaurantId=${restaurantId}`,
      {}
    );

    if (res) {
      if (res.success) {
        driverOrdersApi.setData((prev) => {
          if (!prev?.data) return prev;
          return {
            ...prev,
            data: prev.data.filter((order) => order.id !== orderId),
          };
        });
        setDriverCartCount(driverCartCount + 1);
        startDelivery(orderId)
      }
      setDeliverToast({ message: res.message, success: res.success });
      setTimeout(() => {
        setDeliverToast(null);
      }, 4000);
    }
  };

  return (
    <div id="home-container">
      DriverOrders
      {driverOrdersApi.data?.data &&
        driverOrdersApi.data.data.map((order) => (
          <div>
            id:{order.id} - mkd:{order.amount} - status:{order.orderStatus} -
            customer:{order.userId} -
            <button onClick={() => deliverOrder(order.id, order.restaurantId)}>
              deliver
            </button>
          </div>
        ))}
      {deliverToast && (
        <div
          className={`toast ${
            deliverToast.success ? "success-toast" : "fail-toast"
          }`}
        >
          {deliverToast.message}
        </div>
      )}
      {newOrderToast && (
        <div className="toast success-toast">{newOrderToast}</div>
      )}
    </div>
  );
};

export default DriverOrdersPage;
