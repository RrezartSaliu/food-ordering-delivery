import "../style/Navbar.css";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../util/AuthProvider";
import { FaShoppingCart } from "react-icons/fa";
import { useEffect, useState } from "react";
import { useApi } from "../hooks/useApi";
import type { ShoppingCart } from "../types/ShoppingCart";
import { useCart } from "../util/CartContext";

const Navbar = () => {
  const { token } = useAuth();
  const { role, logout } = useAuth();
  const navigate = useNavigate()
  const shoppingCartApi = useApi<ShoppingCart>(
    `${import.meta.env.VITE_API_URL}shopping-cart/get-shopping-cart`,
    token
  );
  const {cartCount, setCartCount} = useCart()

  const [ shoppingCart, setShoppingCart ] = useState<ShoppingCart|null>(null)

  useEffect(() => {
    const getShoppingCart = async () => {
    const res = await shoppingCartApi.get();
    if (res) {
      setCartCount(res.data.items.length)
      setShoppingCart(res.data)
    }
  };

  getShoppingCart()

    
  }, [setCartCount]);

  return (
    <div id="navbar">
      <div className="center">
        {token !== "" ? (
          <Link to="/profile" id="login-button">
            Profile
          </Link>
        ) : null}
      </div>
      <div className="right">
        {token === "" ? (
          <Link to="/login" id="login-button">
            LOGIN
          </Link>
        ) : null}
        {token && role === "ROLE_USER" && (
          <div onClick={() => {navigate('/shopping-cart')
          }} id="cart-logo-container">
            {cartCount !==0 ? (<div id="cart-badge">{cartCount}</div>):<></>}
            <FaShoppingCart size="3em" color="white" />
          </div>
        )}
        {token !== "" ? (
          <Link
            to="/login"
            id="login-button"
            onClick={() => {
              logout();
            }}
          >
            LOGOUT
          </Link>
        ) : null}
      </div>
    </div>
  );
};

export default Navbar;
