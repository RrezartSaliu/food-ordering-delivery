import type { DriverProfile as DriverProfileType } from "../types/Profile";

interface Props {
    profile: DriverProfileType
}

const DriverProfile =({profile}: Props)=> {
    return(
        <div id="home-container">
            <h1>Driver Profile</h1>
            <p>ID: {profile.id}</p>
            <p>first name: {profile.firstName}</p>
            <p>last name: {profile.lastName}</p>
            <p>role: {profile.role}</p>
            <p>vehicle: {profile.vehicle}</p>
        </div>
    )
}

export default DriverProfile