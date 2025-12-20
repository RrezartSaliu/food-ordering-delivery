import { useState } from "react"
import { Link } from "react-router-dom"
import { useApi } from "../hooks/useApi"

const RegisterUserPage = () => {
    const [ firstName, setFirstName ] = useState('')
    const [ lastName, setLastName ] = useState('')
    const [ password, setPassword ] = useState('')
    const [ secondPassword, setSecondPassword ] = useState('')
    const [ email, setEmail ] = useState('')
    const registerApi = useApi("http://localhost:8080/auth/register-customer-user")

    const register = (element: React.FormEvent<HTMLFormElement>) => {
        element.preventDefault()

        console.log('pw', password);
        console.log('spw', secondPassword);
        
        
        if(password == secondPassword){
            const body = {
                firstName: firstName,
                lastName: lastName,
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
                <h1>Sign up - User</h1>
                <form method="post" id='form' onSubmit={register}>
                    <label>First Name</label>
                    <input type="text" className='input-field' onChange={(e)=>{setFirstName(e.target.value)}}></input>
                    <label>Last Name</label>
                    <input type="text" className='input-field' onChange={(e)=>{setLastName(e.target.value)}}></input>
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

export default RegisterUserPage