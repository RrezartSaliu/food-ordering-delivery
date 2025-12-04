import { Link, useNavigate } from 'react-router-dom'
import '../style/AuthFormPages.css'
import { useState } from 'react'
import axios from 'axios'
import { useLocalStorage } from '../hooks/useLocalStorage'

const LoginPage = () => {
    const [ email, setEmail ] = useState('')
    const [ password, setPassword ] = useState('')
    const [ token, setToken ] = useLocalStorage("token", "")
    const navigate = useNavigate()

    const login = (element: React.FormEvent<HTMLFormElement>) => {
        element.preventDefault()
        console.log("email", email)
        console.log('password', password)

        const body = {
            email: email,
            password: password
        }

        axios.post('http://localhost:8080/auth/generateToken', body).then((res)=>{
            setToken(res.data)
            if(res.data != '')
                navigate('/profile')
        })
    } 

    return(
        <div id='container'>
            <div id="login-box">
                <div></div>
                <h1>LOGIN</h1>
                <form method="post" id='form' onSubmit={login}>
                    <label>Email</label>
                    <input type="text" className='input-field' onChange={(e) => setEmail(e.target.value)}></input>
                    <label>Password</label>
                    <input type="password" className='input-field' onChange={(e) => setPassword(e.target.value)}></input>
                    <input type="submit" className='btn'></input>
                    <Link to='/register-user'>Register as user</Link>
                    <Link to='/register-restaurant'>Register as restaurant</Link>
                </form>
            </div>
        </div>
    )
}

export default LoginPage