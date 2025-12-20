import "../style/Home.css";
import type { Product } from "../types/Product";
import { useEffect, useState } from "react";
import { useApi } from "../hooks/useApi";
import { useAuth } from "../util/AuthProvider";
import { useCart } from "../util/CartContext";

const Home = () => {
    const { token } = useAuth()
  const [products, setProducts] = useState<Product[] | null>();
  const {cartCount, setCartCount} = useCart()
  const call = useApi<Product[]>(
    "http://localhost:8080/restaurant/public/restaurant-items?restaurantId=21"
  );
  const addItemApi = useApi<Product>("http://localhost:8080/shopping-cart/add-item", token)

  useEffect(() => {
    const fetchProducts = async () => {
      const res = await call.get();
      if (res) {
        setProducts(res.data);
      }
    };
    fetchProducts();
  }, []);

  const addToCart = (id: number) => {
    const body = {
            "menuItemId": id,
            "quantity": 1  
        }

    addItemApi.post("", body).then((res)=>{
        console.log(res?.data);
        setCartCount(cartCount+1)
    }
    )
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
      {products?.map((product) => <Card key={product.id} {...product}></Card>)}
    </div>
  );
};

export default Home;
