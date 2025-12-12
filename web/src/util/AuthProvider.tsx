import { useState, createContext, useContext } from "react";
import type { ReactNode } from "react";

interface AuthContextType {
    token: string
    setToken: (token: string) => void
}

const AuthContext = createContext<AuthContextType | undefined>(undefined)

export const AuthProvider = ({ children }: { children: ReactNode }) => {
    const [token, setToken] = useState(() => localStorage.getItem("token") || "");

    const updateToken = (newToken: string) => {
        setToken(newToken)
        localStorage.setItem("token", newToken)
    }

    return(
        <AuthContext.Provider value={{ token, setToken: updateToken }}>
            {children}
        </AuthContext.Provider>
    )
}

export const useAuth = () => {
    const context = useContext(AuthContext)
    if (!context) throw new Error("useAuth must be used within AuthProvider")
    return context
}