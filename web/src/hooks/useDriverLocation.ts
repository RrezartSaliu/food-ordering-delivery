import { useEffect, useRef } from "react";
import { useWebSocket } from "../websocket/WebsocketProvider";

export const useDriverLocation = (orderIds: number[]) => {
  const { send } = useWebSocket();
  const intervalRef = useRef<number | null>(null);

  useEffect(() => {
    if (intervalRef.current) {
      clearInterval(intervalRef.current);
      intervalRef.current = null;
    }

    if (orderIds.length === 0) return;

    intervalRef.current = window.setInterval(() => {
      navigator.geolocation.getCurrentPosition(
        (pos) => {
          const payload = {
            type: "DRIVER_LOCATION",
            lat: pos.coords.latitude,
            lng: pos.coords.longitude,
          };

          orderIds.forEach(orderId => {
            console.log("Sending location", orderId, payload);
            send({ ...payload, orderId });
          });
        },
        (err) => console.error("GPS error", err),
        { enableHighAccuracy: true }
      );
    }, 5000);

    return () => {
      if (intervalRef.current) {
        clearInterval(intervalRef.current);
        intervalRef.current = null;
      }
    };
  }, [orderIds.join(","), send]);
};
