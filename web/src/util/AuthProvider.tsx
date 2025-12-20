import { useState, createContext, useContext } from "react";
import type { ReactNode } from "react";
import { jwtDecode } from "jwt-decode";

interface JwtPayload {
  role?: string;
}

interface AuthContextType {
  token: string;
  setToken: (token: string) => void;
  role: string | null;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [token, setTokenState] = useState(
    () => localStorage.getItem("token") || ""
  );
  const [role, setRole] = useState<string | null>(() => {
    const stored = localStorage.getItem("token");
    if (!stored) return null;
    try {
      const decoded = jwtDecode<JwtPayload>(stored);
      return decoded.role ?? null;
    } catch {
      return null;
    }
  });

  const setToken = (newToken: string) => {
    setTokenState(newToken);
    localStorage.setItem("token", newToken);

    try {
      const decoded = jwtDecode<JwtPayload>(newToken);
      setRole(decoded.role ?? null);
    } catch {
      setRole(null);
    }
  };

  const logout = () => {
    setTokenState("");
    setRole(null);
    localStorage.removeItem("token");
  };

  return (
    <AuthContext.Provider value={{ token, role, setToken, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) throw new Error("useAuth must be used within AuthProvider");
  return context;
};
