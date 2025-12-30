import { createContext, useContext, useState } from "react";

interface CartContextType {
  cartCount: number;
  setCartCount: (count: number) => void;
  driverCartCount: number;
  setDriverCartCount: (count: number) => void
}

const CartContext = createContext<CartContextType | undefined>(undefined);

export const CartProvider = ({ children }: { children: React.ReactNode }) => {
  const [cartCount, setCartCount] = useState(0);
  const [driverCartCount, setDriverCartCount] = useState(0)

  return (
    <CartContext.Provider value={{ cartCount, setCartCount, driverCartCount, setDriverCartCount }}>
      {children}
    </CartContext.Provider>
  );
};

export const useCart = () => {
  const context = useContext(CartContext);
  if (!context) throw new Error("useCart must be used within CartProvider");
  return context;
};
