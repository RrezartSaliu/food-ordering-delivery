import { useNavigate } from "react-router-dom"
import { useLocalStorage } from "../hooks/useLocalStorage"

const ProfilePage = () => {
    const [ token, setToken ] = useLocalStorage("token", "")
    const navigate = useNavigate()

    const logout = () => {
        setToken("")
        navigate("/login")
    }

    return (
        <div>
            Profile
            <input type="button" value="logout" onClick={logout}/>
        </div>
    )
}

export default ProfilePage