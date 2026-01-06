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
  const isConnectedRef = useRef(false);
  const queueRef = useRef<any[]>([]);


  useEffect(() => {
  console.log(
    "WS effect",
    "token:", !!token,
    "wsRef:", wsRef.current?.readyState
  );

  // logout cleanup
  if (!token) {
    queueRef.current = [];
    isConnectedRef.current = false;
    wsRef.current?.close();
    wsRef.current = null;
    return;
  }

  // already connected or connecting
  if (
    wsRef.current &&
    (wsRef.current.readyState === WebSocket.OPEN ||
     wsRef.current.readyState === WebSocket.CONNECTING)
  ) {
    return;
  }

  const decoded: any = jwtDecode(token);
  const userId = decoded.id;

  console.log("Creating WebSocket for user", userId);

  wsRef.current = new WebSocket(
    `ws://localhost:8080/ws?userId=${userId}`
  );

  wsRef.current.onopen = () => {
    console.log("WS connected ✅");
    isConnectedRef.current = true;

    queueRef.current.forEach(msg =>
      wsRef.current?.send(JSON.stringify(msg))
    );
    queueRef.current = [];
  };

  wsRef.current.onmessage = event => {
    setLastMessage(JSON.parse(event.data));
  };

  wsRef.current.onclose = () => {
    console.log("WS disconnected ❌");
    isConnectedRef.current = false;
    wsRef.current = null;
  };

  wsRef.current.onerror = err => {
    console.error("WS error", err);
  };
}, [token]);


  const send = (data: any) => {
  if (wsRef.current && isConnectedRef.current) {
    wsRef.current.send(JSON.stringify(data));
  } else {
    queueRef.current.push(data);
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
