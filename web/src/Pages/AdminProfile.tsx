import { useEffect } from "react"
import { useApi } from "../hooks/useApi"
import type { AdminProfile as AdminProfileType } from "../types/Profile"
import type { RestaurantProfile as RestaurantProfileType } from "../types/Profile"
import { useAuth } from "../util/AuthProvider"

interface Props {
    profile: AdminProfileType
}

const AdminProfile =({profile}: Props) => {
    const { token } = useAuth()
    const fetchRestaurantApi = useApi<RestaurantProfileType[]>(`${import.meta.env.VITE_API_URL}user/admin/get-restaurants`, token)
    const fetchVerifiedRestaurant = useApi<RestaurantProfileType>(`${import.meta.env.VITE_API_URL}user/admin/verify-restaurant`, token)
    const verifyRestaurant = async (restaurant: RestaurantProfileType)=>{
        const res = await fetchVerifiedRestaurant.post("", restaurant)

        if(res?.success){
            fetchRestaurantApi.setData(prev=>
                prev?{
                    ...prev,
                    data: prev.data.map(r=>
                        r.id ===restaurant.id ?{ ...r, verified: true}:r
                    ),
                }
                :prev
            )
        }
    }

    useEffect(()=>{
        fetchRestaurantApi.get().then(res=>console.log(res?.data)
        )
    }, [])

    return (
        <div id="home-container">
            <h1>Admin Profile</h1>
            <p>ID: {profile.id}</p>
            <p>Email: {profile.email}</p>
            <p>Role: {profile.role}</p>
            <p>Username: {profile.username}</p>
            {fetchRestaurantApi.data?.data.map((restaurant)=>(<div key={restaurant.id}>{restaurant.name}{restaurant.email}-{restaurant.verified?"Verified":(<>Unverified - <button onClick={()=>verifyRestaurant(restaurant)}>Verify</button></>)}</div>))}
        </div>
    )
}

export default AdminProfile