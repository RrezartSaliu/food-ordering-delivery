import "../style/Home.css";
import type { Product } from "../types/Product";
import { useEffect, useState } from "react";
import { useApi } from "../hooks/useApi";
import { useAuth } from "../util/AuthProvider";
import { useCart } from "../util/CartContext";
import type { Order } from "../types/Order";
import { useDriverDelivery } from "../util/DriverDeliveryContext";

const Home = () => {
  const { token, role } = useAuth();
  const [products, setProducts] = useState<Product[] | null>();
  const { cartCount, setCartCount } = useCart();
  const {startDelivery} = useDriverDelivery()
  const call = useApi<Product[]>(
    `${
      import.meta.env.VITE_API_URL
    }restaurant/public/restaurant-items?restaurantId=21`
  );
  const addItemApi = useApi<Product>(
    `${import.meta.env.VITE_API_URL}shopping-cart/add-item`,
    token
  );
  const itemsCountApi = useApi<number>(
    `${import.meta.env.VITE_API_URL}shopping-cart/get-shopping-cart-count`,
    token
  );
  const { setDriverCartCount } = useCart();
  const driverCartApi = useApi<number>(
    `${import.meta.env.VITE_API_URL}order/driver-cart-count`,
    token
  );
  const driverCartOrderApi = useApi<Order[]|null>(`${import.meta.env.VITE_API_URL}order/driver-cart`, token)

  useEffect(() => {
    const fetchProducts = async () => {
      const res = await call.get();
      if (res) {
        setProducts(res.data);
      }
    };
    fetchProducts();

    itemsCountApi.get().then((res) => {
      if (res) setCartCount(res.data);
    });

    if (role === "ROLE_DRIVER") {
      driverCartApi.get().then((res) => {
        if (res?.success) {
          setDriverCartCount(res.data);
          driverCartOrderApi.get().then(res=>{
            if (res?.success && res.data){
              res.data.map(order=>{
                startDelivery(order.id)
              })
            }
          })
        }
      });
    }
  }, [role, token]);

  const addToCart = (id: number) => {
    const body = {
      menuItemId: id,
      quantity: 1,
    };

    addItemApi.post("", body).then((res) => {
      console.log(res?.data);
      setCartCount(cartCount + 1);
    });
  };

  const Card = (product: Product) => {
    return (
      <div>
        {product.name} -{product.price} MKD
        <button onClick={() => addToCart(product.id)}>add to cart</button>
      </div>
    );
  };

  return (
    <div id="home-container">
      <div>Most ordered meals</div>
      {products ? (
        products?.map((product) => <Card key={product.id} {...product}></Card>)
      ) : (
        <div>Loading</div>
      )}
    </div>
  );
};

export default Home;
