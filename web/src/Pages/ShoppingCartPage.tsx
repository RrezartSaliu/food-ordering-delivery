import { useEffect, useState } from "react";
import { useApi } from "../hooks/useApi";
import type { ShoppingCart } from "../types/ShoppingCart";
import { useAuth } from "../util/AuthProvider";
import { useCart } from "../util/CartContext";

const ShoppingCartPage = () => {
  const { token } = useAuth();
  const { cartCount, setCartCount } = useCart();
  const [shoppingCart, setShoppingCart] = useState<ShoppingCart | null>(null);
  const shoppingCartApi = useApi<ShoppingCart>(
    "http://localhost:8080/shopping-cart/get-shopping-cart",
    token
  );
  const removeItemApi = useApi<ShoppingCart>(
    "http://localhost:8080/shopping-cart/remove-item",
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
    </div>
  );
};

export default ShoppingCartPage;
