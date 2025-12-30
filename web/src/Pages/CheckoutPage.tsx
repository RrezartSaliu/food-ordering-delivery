import { useEffect, useState } from "react";
import { useApi } from "../hooks/useApi";
import { useAuth } from "../util/AuthProvider";
import { jwtDecode } from "jwt-decode";
import { useWebSocket } from "../websocket/WebsocketProvider";

const CheckoutPage = () => {
  const { token } = useAuth();
  const [name, setName] = useState<string>("");
  const [lastName, setLastName] = useState<string>("");
  const [cardNumber, setCardNumber] = useState<string>("");
  const [cvv, setCvv] = useState<string>("");
  const checkoutApi = useApi<string | null>(
    `${import.meta.env.VITE_API_URL}shopping-cart/checkout`,
    token
  );
  const [processingPayment, setProcessingPayment] = useState<boolean>(false);
  const decoded: any = jwtDecode(token);
  const userId = decoded.id;
  const [paymentMade, setPaymentMade] = useState<boolean>(false);
  const [paymentMessage, setPaymentMessage] = useState<string>("");
  const { lastMessage } = useWebSocket();
  const [currentLocation, setCurrentLocation] = useState(false);
  const [coords, setCoords] = useState<GeolocationCoordinates | null>(null);
  const [locationError, setLocationError] = useState<string | null>(null);

  const requestLocation = () => {
    if (!navigator.geolocation) {
      setLocationError("Geolocation not supported");
      return;
    }

    navigator.geolocation.getCurrentPosition(
      (position) => {
        setCoords(position.coords);
        setLocationError(null);
      },
      (error) => {
        setLocationError("Location permission denied");
        setCurrentLocation(false);
      },
      { enableHighAccuracy: false }
    );
  };


  useEffect(() => {
    if (!lastMessage) return;

    if (lastMessage.type === "payment") {
      setProcessingPayment(false);
      setPaymentMade(true);
      setPaymentMessage(lastMessage.status);
    }
  }, [lastMessage]);

  const checkout = () => {
    if (coords === null)
    {
      alert("no coordinates")
      return
    }
    const cardNum = Number(cardNumber);
    const cvvNum = Number(cvv);

    console.log(userId);

    if (
      !isNaN(cardNum) &&
      !isNaN(cvvNum) &&
      cardNumber.length === 16 &&
      cvv.length === 3
    ) {
      const body = {
        firstName: name,
        lastName: lastName,
        cardNumber: cardNumber,
        cvv: cvv,
        longitude: coords.longitude,
        latitude: coords.latitude,
        address: "Current user location"
      };
      checkoutApi.post("", body);
      setProcessingPayment(true);
    } else {
      console.log("Invalid card number or CVV");
    }
  };

  const form = () => {
    return (
      <>
        Checkout page:
        <div>
          <label>Card Holder First Name</label>
          <input type="text" onChange={(e) => setName(e.target.value)}></input>
        </div>
        <div>
          <label>Card Holder Last Name</label>
          <input
            type="text"
            onChange={(e) => setLastName(e.target.value)}
          ></input>
        </div>
        <div>
          <label>Card Number</label>
          <input
            style={{
              border:
                cardNumber.length === 0
                  ? ""
                  : cardNumber.length === 16
                    ? ""
                    : "6px red solid",
            }}
            type="text"
            onChange={(e) => setCardNumber(e.target.value)}
          ></input>
        </div>
        <div>
          <label>CVV</label>
          <input
            style={{
              border:
                cvv.length === 0 ? "" : cvv.length === 3 ? "" : "6px red solid",
            }}
            type="text"
            onChange={(e) => setCvv(e.target.value)}
          ></input>
        </div>
        <div>
          <label>use my current location</label>
          <input
            type="checkbox"
            checked={currentLocation}
            onChange={(e) => {
              const checked = e.target.checked;
              setCurrentLocation(checked)
              
              if (checked) {
                requestLocation();
              } else {
                setCoords(null);
              }
            }}
          />
        </div>
        {/* <div>
          <label>Address</label>
          <input type="text" disabled={currentLocation}/>
        </div> */}
        <button onClick={() => {
          checkout()
        }}>Confirm</button>
      </>
    );
  };

  return (
    <div id="container">
      {processingPayment ? (
        <div>proccessing payment...</div>
      ) : paymentMade ? (
        <div>{paymentMessage}</div>
      ) : (
        form()
      )}
    </div>
  );
};

export default CheckoutPage;
