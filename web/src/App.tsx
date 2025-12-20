import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Home from './Pages/Home'
import LoginPage from './Pages/LoginPage'
import RegisterUserPage from './Pages/RegisterUserPage'
import RegisterRestaurantPage from './Pages/RegisterRestaurantPage'
import ProfilePage from './Pages/ProfilePage'
import Navbar from './components/Navbar'
import { AuthProvider } from './util/AuthProvider'
import { CartProvider } from './util/CartContext'
import ShoppingCartPage from './Pages/ShoppingCartPage'
import CheckoutPage from './Pages/CheckoutPage'

function App() {
  return(
    <AuthProvider>
      <BrowserRouter>
        <CartProvider>
          <Navbar/>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<LoginPage/>} />
            <Route path="/register-user" element={<RegisterUserPage/>}/>
            <Route path="/register-restaurant" element={<RegisterRestaurantPage/>}/>
            <Route path="/profile" element={<ProfilePage/>}/>
            <Route path="/shopping-cart" element={<ShoppingCartPage/>}/>
            <Route path="/checkout" element={<CheckoutPage/>}/>
          </Routes>
        </CartProvider>
      </BrowserRouter>
    </AuthProvider>
  )
}

export default App
