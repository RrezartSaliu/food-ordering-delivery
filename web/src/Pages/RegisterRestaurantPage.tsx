import { Link } from "react-router-dom"
import { useState } from 'react'
import { useApi } from "../hooks/useApi"

const RegisterRestaurantPage = () => {
    const [ name, setName ] = useState('')
    const [ address, setAddress ] = useState('')
    const [ password, setPassword ] = useState('')
    const [ secondPassword, setSecondPassword ] = useState('')
    const [ email, setEmail ] = useState('')
    const registerApi = useApi<string>(`${import.meta.env.VITE_API_URL}auth/register-restaurant-user`)

    const register = (element: React.FormEvent<HTMLFormElement>) => {
        element.preventDefault()        
        
        if(password == secondPassword){
            const body = {
                name: name,
                address: address,
                password: password,
                email: email
            }

            registerApi.post("", body)

        }
    }

    return(
        <div id='container'>
            <div id="login-box">
                <div></div>
                <h1>Sign up - Restaurant</h1>
                <form method="post" id='form' onSubmit={register}>
                    <label>Name</label>
                    <input type="text" className='input-field' onChange={(e)=>{setName(e.target.value)}}></input>
                    <label>Address</label>
                    <input type="text" className='input-field' onChange={(e)=>{setAddress(e.target.value)}}></input>
                    <label>Email</label>
                    <input type="text" className='input-field' onChange={(e)=>{setEmail(e.target.value)}}></input>
                    <label>Password</label>
                    <input type="password" className='input-field' onChange={(e)=>{setPassword(e.target.value)}}></input>
                    <label>Repeat Password</label>
                    <input type="password" className='input-field' onChange={(e)=>{setSecondPassword(e.target.value)}}></input>
                    <input type="submit" className='btn'></input>
                    <Link to='/login'>Login</Link>
                </form>
            </div>
        </div>
    )
}

export default RegisterRestaurantPage