import { useDriverDelivery } from "../util/DriverDeliveryContext";
import { useDriverLocation } from "../hooks/useDriverLocation";
import { useAuth } from "../util/AuthProvider";

export const DriverLocationTracker = () => {
  const { token, role } = useAuth()
  if (token && role === "ROLE_DRIVER"){
    const { activeOrderIds } = useDriverDelivery();
    useDriverLocation(activeOrderIds);
  }
  return null;
};
