import { BrowserRouter, Route, Routes } from "react-router-dom";
import Home from "./Pages/Home";
import LoginPage from "./Pages/LoginPage";
import RegisterUserPage from "./Pages/RegisterUserPage";
import RegisterRestaurantPage from "./Pages/RegisterRestaurantPage";
import ProfilePage from "./Pages/ProfilePage";
import Navbar from "./components/Navbar";
import { AuthProvider } from "./util/AuthProvider";
import { CartProvider } from "./util/CartContext";
import ShoppingCartPage from "./Pages/ShoppingCartPage";
import CheckoutPage from "./Pages/CheckoutPage";
import { WebSocketProvider } from "./websocket/WebsocketProvider";
import ProtectedRoute from "./ProtectedRoute";
import DriverManagementPage from "./Pages/DriverManagementPage";
import VerifyRestaurantsPage from "./Pages/VerifyRestaurantsPage";
import UserOrdersPage from "./Pages/UserOrdersPage";
import RestaurantItemsManagementPage from "./Pages/RestaurantItemsManagementPage";
import RestaurantOrdersPage from "./Pages/RestaurantOrdersPage";
import DriverOrdersPage from "./Pages/DriverOrdersPage";
import DriverCartPage from "./Pages/DriverCartPage";
import OrderDetailPage from "./Pages/OrderDetailPage";

function App() {
  return (
    <AuthProvider>
      <WebSocketProvider>
        <BrowserRouter>
          <CartProvider>
            <Navbar />
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/login" element={<LoginPage />} />
              <Route path="/register-user" element={<RegisterUserPage />} />
              <Route
                path="/register-restaurant"
                element={<RegisterRestaurantPage />}
              />
              <Route path="/profile" element={<ProfilePage />} />
              <Route
                path="/shopping-cart"
                element={
                  <ProtectedRoute allowedRoles={["ROLE_USER"]}>
                    <ShoppingCartPage />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/checkout"
                element={
                  <ProtectedRoute allowedRoles={["ROLE_USER"]}>
                    <CheckoutPage />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/driver-management"
                element={
                  <ProtectedRoute allowedRoles={["ROLE_RESTAURANT"]}>
                    <DriverManagementPage/>
                  </ProtectedRoute>
                }
              />
              <Route
                path="/verify-restaurants"
                element={
                  <ProtectedRoute allowedRoles={["ROLE_ADMIN"]}>
                    <VerifyRestaurantsPage/>
                  </ProtectedRoute>
                }
              />
              <Route
                path="/user-orders"
                element={
                  <ProtectedRoute allowedRoles={["ROLE_USER"]}>
                    <UserOrdersPage/>
                  </ProtectedRoute>
                }
              />
              <Route
                path="/restaurant-items-management"
                element={
                  <ProtectedRoute allowedRoles={["ROLE_RESTAURANT"]}>
                    <RestaurantItemsManagementPage/>
                  </ProtectedRoute>
                }
              />
              <Route
                path="/restaurant-orders-page"
                element={
                  <ProtectedRoute allowedRoles={["ROLE_RESTAURANT"]}>
                    <RestaurantOrdersPage/>
                  </ProtectedRoute>
                }
              />
              <Route
                path="/driver-orders"
                element={
                  <ProtectedRoute allowedRoles={["ROLE_DRIVER"]}>
                    <DriverOrdersPage/>
                  </ProtectedRoute>
                }
              />
              <Route
                path="/driver-cart"
                element={
                  <ProtectedRoute allowedRoles={["ROLE_DRIVER"]}>
                    <DriverCartPage/>
                  </ProtectedRoute>
                }
              />
              <Route path="/order/:id" element={
                <ProtectedRoute allowedRoles={["ROLE_DRIVER", "ROLE_USER", "ROLE_ADMIN", "ROLE_RESTAURANT"]}>
                  <OrderDetailPage/>
                </ProtectedRoute>
              }/>
            </Routes>
          </CartProvider>
        </BrowserRouter>
      </WebSocketProvider>
    </AuthProvider>
  );
}

export default App;
