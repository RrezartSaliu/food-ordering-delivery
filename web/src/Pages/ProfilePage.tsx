import axios from "axios"
import { useEffect, useState } from "react"
import { useAuth } from "../util/AuthProvider"
import type { Profile } from "../types/Profile"
import CustomerProfile from "./CustomerProfile"
import RestaurantProfile from "./RestaurantProfile"

const ProfilePage = () => {
    const [ profile, setProfile ] = useState<Profile | null>(null)
    const { token } = useAuth()
    
    useEffect(()=>{
        axios.get("http://localhost:8080/user/profile", {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }).then((res)=> {
            console.log(res.data);
            setProfile(res.data.data)
        })
    }, [])

    if (!profile) return <div>LOADING ...</div>

    switch (profile.role) {
        case "ROLE_USER":
            return <CustomerProfile profile={profile}/>
        case "ROLE_RESTAURANT":
            return  <RestaurantProfile profile={profile}/>
    }
}

export default ProfilePage