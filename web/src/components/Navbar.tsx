import { useLocalStorage } from '../hooks/useLocalStorage';
import '../style/Navbar.css'
import { Link } from 'react-router-dom';
import { useAuth } from '../util/AuthProvider';

const Navbar = () => {
    const { token } = useAuth()
    const { setToken } = useAuth()

    return(
        <div id='navbar'>
            <div className='center'>
                {
                    token !== ""?<Link to="/profile" id="login-button">Profile</Link>:null
                }
            </div>
            <div className='right'>
                {
                    token === ""?<Link to="/login" id="login-button">LOGIN</Link>:null
                }
                {
                    token !== ""?<Link to="/login" id="login-button" onClick={()=>{setToken("")}}>LOGOUT</Link>:null
                }
            </div>
        </div>
    )
}

export default Navbar;