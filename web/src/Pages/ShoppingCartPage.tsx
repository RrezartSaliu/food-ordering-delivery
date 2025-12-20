import { useEffect, useState } from "react";
import { useApi } from "../hooks/useApi";
import type { ShoppingCart } from "../types/ShoppingCart";
import { useAuth } from "../util/AuthProvider";
import { useCart } from "../util/CartContext";
import { useNavigate } from "react-router-dom";

const ShoppingCartPage = () => {
  const { token } = useAuth();
  const { cartCount, setCartCount } = useCart();
  const [shoppingCart, setShoppingCart] = useState<ShoppingCart | null>(null);
  const navigate = useNavigate()
  const shoppingCartApi = useApi<ShoppingCart>(
    `${import.meta.env.VITE_API_URL}shopping-cart/get-shopping-cart`,
    token
  );
  const removeItemApi = useApi<ShoppingCart>(
    `${import.meta.env.VITE_API_URL}shopping-cart/remove-item`,
    token
  )

  useEffect(() => {
    const getShoppingCart = async () => {
      const res = await shoppingCartApi.get();
      if (res) {
        setCartCount(res.data.items.length);
        setShoppingCart(res.data);
        console.log(res.data);
      }
    };
    getShoppingCart();

  }, [setCartCount, cartCount]);

  const removeItem = async (id: number) =>{
    const res = await removeItemApi.del(`/${id}`)
    if (res){
        setCartCount(cartCount-1)
    }
  }

  const total =
    shoppingCart?.items.reduce(
      (acc, item) => acc + item.quantity * item.menuItemSnapshot.price,
      0
    ) || 0;

  return (
    <div id="container">
      <div>Shopping Cart</div>
      {shoppingCart?.items.map((item)=>(<div key={item.id}>{item.menuItemSnapshot.name} {item.menuItemSnapshot.price} - quantity - {item.quantity} <button onClick={()=>removeItem(item.id)}>remove</button></div>))}
      Total {total}
      <button onClick={()=>navigate('/checkout')}>Go to checkout</button>
    </div>
  );
};

export default ShoppingCartPage;
