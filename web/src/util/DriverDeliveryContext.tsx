import { createContext, useContext, useState } from "react";

interface DriverDeliveryContextType {
  activeOrderIds: number[];
  startDelivery: (orderId: number) => void;
  stopDelivery: (orderId: number) => void;
}

const DriverDeliveryContext = createContext<DriverDeliveryContextType | null>(null);

export const DriverDeliveryProvider = ({ children }: { children: React.ReactNode }) => {
  const [activeOrderIds, setActiveOrderIds] = useState<number[]>([]);

  const startDelivery = (orderId: number) => {
    setActiveOrderIds(prev =>
      prev.includes(orderId) ? prev : [...prev, orderId]
    );
  };

  const stopDelivery = (orderId: number) => {
    setActiveOrderIds(prev => prev.filter(id => id !== orderId));
  };

  return (
    <DriverDeliveryContext.Provider
      value={{ activeOrderIds, startDelivery, stopDelivery }}
    >
      {children}
    </DriverDeliveryContext.Provider>
  );
};

export const useDriverDelivery = () => {
  const ctx = useContext(DriverDeliveryContext);
  if (!ctx) throw new Error("useDriverDelivery must be used inside DriverDeliveryProvider");
  return ctx;
};
