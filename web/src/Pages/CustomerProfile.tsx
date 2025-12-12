import type { CustomerProfile as CustomerProfileType } from "../types/Profile";

interface Props {
    profile: CustomerProfileType;
}

const CustomerProfile = ({profile}: Props) => {
    return (
        <div id="container">
            <h1>Customer Profile</h1>
            <p>Email: {profile.email}</p>
            <p>First name: {profile.firstName}</p>
            <p>Last name: {profile.lastName}</p>
        </div>
    )
}

export default CustomerProfile