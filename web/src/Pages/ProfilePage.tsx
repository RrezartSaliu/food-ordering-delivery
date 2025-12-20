import axios from "axios"
import { useEffect, useState } from "react"
import { useAuth } from "../util/AuthProvider"
import type { Profile } from "../types/Profile"
import CustomerProfile from "./CustomerProfile"
import RestaurantProfile from "./RestaurantProfile"
import { useApi } from "../hooks/useApi"

const ProfilePage = () => {
    const [ profile, setProfile ] = useState<Profile | null>(null)
    const { token } = useAuth()
    const profileApi = useApi("http://localhost:8080/user/profile", token)


    
    useEffect(()=>{
        const fetchProfile = async () =>{
            const res = await profileApi.get()
            if (res){
                setProfile(res.data)
            }
        }
        fetchProfile()
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