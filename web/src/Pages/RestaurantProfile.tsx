import type { RestaurantProfile as RestaurantPofileType } from "../types/Profile"

interface Prop {
    profile: RestaurantPofileType
}

const RestaurantProfile = ({ profile }: Prop) => {
    return (
        <div id="container">
            <h1>Restaurant Profile</h1>
            <p>email: {profile.email}</p>
            <p>name: {profile.name}</p>
            <p>address: {profile.address}</p>
        </div>
    )
}

export default RestaurantProfile