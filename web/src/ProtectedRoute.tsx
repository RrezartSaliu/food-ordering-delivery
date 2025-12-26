import type { JSX } from "react"
import { useAuth } from "./util/AuthProvider"
import { Navigate } from "react-router-dom"

interface Props {
    allowedRoles: string[]
    children: JSX.Element
}

const ProtectedRoute = ({ allowedRoles, children}: Props) => {
    const { token, role } = useAuth()

    if ( !token || !role || !allowedRoles.includes(role))
        return <Navigate to="/" replace></Navigate>

    return children
}

export default ProtectedRoute