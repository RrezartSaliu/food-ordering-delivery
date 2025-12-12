import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Home from './Pages/Home'
import LoginPage from './Pages/LoginPage'
import RegisterUserPage from './Pages/RegisterUserPage'
import RegisterRestaurantPage from './Pages/RegisterRestaurantPage'
import ProfilePage from './Pages/ProfilePage'
import Navbar from './components/Navbar'
import { AuthProvider } from './util/AuthProvider'

function App() {
  return(
    <AuthProvider>
      <BrowserRouter>
        <Navbar/>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<LoginPage/>} />
            <Route path="/register-user" element={<RegisterUserPage/>}/>
            <Route path="/register-restaurant" element={<RegisterRestaurantPage/>}/>
            <Route path="/profile" element={<ProfilePage/>}/>
          </Routes>
      </BrowserRouter>
    </AuthProvider>
  )
}

export default App
