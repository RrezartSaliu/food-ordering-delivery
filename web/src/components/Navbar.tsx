import "../style/Navbar.css";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../util/AuthProvider";
import { FaShoppingCart, FaUser } from "react-icons/fa";
import { useEffect, useState } from "react";
import { useApi } from "../hooks/useApi";
import type { ShoppingCart } from "../types/ShoppingCart";
import { useCart } from "../util/CartContext";

const Navbar = () => {
  const { token } = useAuth();
  const { role, logout } = useAuth();
  const navigate = useNavigate();
  const shoppingCartApi = useApi<ShoppingCart>(
    `${import.meta.env.VITE_API_URL}shopping-cart/get-shopping-cart`,
    token
  );
  const { cartCount, setCartCount } = useCart();
  const [profileIconClicked, setProfileIconClicked] = useState<boolean>(false);

  useEffect(() => {
    const getShoppingCart = async () => {
      const res = await shoppingCartApi.get();
      if (res) {
        setCartCount(res.data.items.length);
      }
    };

    getShoppingCart();
  }, [setCartCount]);

  return (
    <div id="navbar">
      <div className="left">
        <div style={{width: '80px'}}></div>
      </div>
      <div className="center">
        { role && ["ROLE_RESTAURANT", "ROLE_USER", "ROLE_DRIVER"].includes(role) &&
        <div className="tabs"
          onClick={()=>{
            switch (role) {
            case "ROLE_USER":
              navigate("/user-orders");
              break;
            case "ROLE_RESTAURANT":
              navigate("/restaurant-orders-page");
              break;
            case "ROLE_DRIVER":
              navigate("/driver-orders")
              break;
          }
          }}
        >ORDERS</div>}
        { role === "ROLE_RESTAURANT" &&
        <div className="tabs" onClick={()=>navigate("/restaurant-items-management")}> 
          PRODUCTS
        </div>
        }
      </div>
      <div className="right">
        {token === "" ? (
          <Link to="/login" id="login-button">
            LOGIN
          </Link>
        ) : null}
        {token && role === "ROLE_USER" && (
          <div
            onClick={() => {
              navigate("/shopping-cart");
            }}
            id="cart-logo-container"
          >
            {cartCount !== 0 ? <div id="cart-badge">{cartCount}</div> : <></>}
            <FaShoppingCart size="2em" color="white" />
          </div>
        )}
        {role ? (
          <>
            <div className="profile-wrapper">
              <FaUser
                className="profile-icon"
                onClick={() => setProfileIconClicked(!profileIconClicked)}
              />

              <div
                className={`profile-dropdown ${
                  profileIconClicked ? "open" : ""
                }`}
              >
                <div
                  onClick={() => {
                    setProfileIconClicked(false);
                    navigate("/profile");
                  }}
                >
                  Profile
                </div>
                {
                  role ==="ROLE_ADMIN" && 
                  <div onClick={()=>{
                    setProfileIconClicked(false);
                    navigate("/verify-restaurants")
                  }}>
                    Restaurants
                  </div>
                }
                {
                  role==="ROLE_RESTAURANT" &&
                  <div onClick={()=>{
                    setProfileIconClicked(false);
                    navigate("/driver-management")
                  }}>
                    Drivers
                  </div>
                }
                <div
                  onClick={() => {
                    setProfileIconClicked(false);
                    logout();
                    navigate("/")
                  }}
                >
                  Logout
                </div>
              </div>
            </div>
          </>
        ) : null}
      </div>
    </div>
  );
};

export default Navbar;
