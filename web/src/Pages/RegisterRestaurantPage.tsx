import { Link } from "react-router-dom"

const RegisterRestaurantPage = () => {
    return(
        <div id='container'>
            <div id="login-box">
                <div></div>
                <h1>Sign up - Restaurant</h1>
                <form method="post" id='form'>
                    <label>Name</label>
                    <input type="text" className='input-field'></input>
                    <label>Address</label>
                    <input type="text" className='input-field'></input>
                    <label>Email</label>
                    <input type="text" className='input-field'></input>
                    <label>Password</label>
                    <input type="password" className='input-field'></input>
                    <label>Repeat Password</label>
                    <input type="password" className='input-field'></input>
                    <input type="submit" className='btn'></input>
                    <Link to='/login'>Login</Link>
                </form>
            </div>
        </div>
    )
}

export default RegisterRestaurantPage