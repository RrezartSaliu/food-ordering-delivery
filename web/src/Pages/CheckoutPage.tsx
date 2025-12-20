import { useState } from "react";
import { useApi } from "../hooks/useApi";
import { useAuth } from "../util/AuthProvider";

const CheckoutPage = () => {
    const {token} = useAuth()
  const [name, setName] = useState<string>("");
  const [lastName, setLastName] = useState<string>("");
  const [cardNumber, setCardNumber] = useState<string>("");
  const [cvv, setCvv] = useState<string>(""); 
  const checkoutApi = useApi<string | null>(`${import.meta.env.VITE_API_URL}shopping-cart/checkout`, token)

  const checkout = () => {
    const cardNum = Number(cardNumber);
    const cvvNum = Number(cvv);

    if (
      !isNaN(cardNum) &&
      !isNaN(cvvNum) &&
      cardNumber.length === 16 &&
      cvv.length === 3
    ) {

      const  body = {
            "firstName": name,
            "lastName": lastName,
            "cardNumber": cardNumber,
            "cvv": cvv
        }
      checkoutApi.post("", body)
    } else {
      console.log("Invalid card number or CVV");
    }
  };

  return (
    <div id="container">
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
      <button onClick={() => checkout()}>Confirm</button>
    </div>
  );
};

export default CheckoutPage;
