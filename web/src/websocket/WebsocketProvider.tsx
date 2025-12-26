import {
  createContext,
  useContext,
  useEffect,
  useRef,
  useState,
} from "react";
import { useAuth } from "../util/AuthProvider";
import { jwtDecode } from "jwt-decode";

type WSMessage = any;

interface WSContextType {
  send: (data: any) => void;
  lastMessage: WSMessage | null;
}

const WebSocketContext = createContext<WSContextType | null>(null);

export const WebSocketProvider = ({ children }: { children: React.ReactNode }) => {
  const { token } = useAuth();
  const wsRef = useRef<WebSocket | null>(null);
  const [lastMessage, setLastMessage] = useState<WSMessage | null>(null);

  useEffect(() => {
    if (!token || wsRef.current) return;

    const decoded: any = jwtDecode(token);
    const userId = decoded.id;

    wsRef.current = new WebSocket(`ws://localhost:8080/ws?userId=${userId}`);

    wsRef.current.onopen = () => {
      console.log("WS connected ✅");
    };

    wsRef.current.onmessage = (event) => {
      const data = JSON.parse(event.data);
      setLastMessage(data);
    };

    wsRef.current.onclose = () => {
      console.log("WS disconnected ❌");
      wsRef.current = null;
    };

    wsRef.current.onerror = (err) => {
      console.error("WS error", err);
    };

    return () => {
      wsRef.current?.close();
      wsRef.current = null;
    };
  }, [token]);

  const send = (data: any) => {
    if (wsRef.current?.readyState === WebSocket.OPEN) {
      wsRef.current.send(JSON.stringify(data));
    }
  };

  return (
    <WebSocketContext.Provider value={{ send, lastMessage }}>
      {children}
    </WebSocketContext.Provider>
  );
};

export const useWebSocket = () => {
  const ctx = useContext(WebSocketContext);
  if (!ctx) throw new Error("useWebSocket must be used inside WebSocketProvider");
  return ctx;
};
