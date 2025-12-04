import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Home from './Pages/Home'
import LoginPage from './Pages/LoginPage'
import RegisterUserPage from './Pages/RegisterUserPage'
import RegisterRestaurantPage from './Pages/RegisterRestaurantPage'
import ProfilePage from './Pages/ProfilePage'

function App() {
  return(
    <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<LoginPage/>} />
          <Route path="/register-user" element={<RegisterUserPage/>}/>
          <Route path="/register-restaurant" element={<RegisterRestaurantPage/>}/>
          <Route path="/profile" element={<ProfilePage/>}/>
        </Routes>
    </BrowserRouter>
  )
}

export default App
