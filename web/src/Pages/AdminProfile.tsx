import type { AdminProfile as AdminProfileType } from "../types/Profile"

interface Props {
    profile: AdminProfileType
}

const AdminProfile =({profile}: Props) => {
    return (
        <div id="home-container">
            <h1>Admin Profile</h1>
            <p>ID: {profile.id}</p>
            <p>Email: {profile.email}</p>
            <p>Role: {profile.role}</p>
            <p>Username: {profile.username}</p>
        </div>
    )
}

export default AdminProfile