import { useState } from "react";
import type { DriverProfile } from "../types/Profile";
import style from "../style/RestaurantProfile.module.css";

interface AddDriverModalProps {
  onClose: () => void;
  onSubmit: (driver: DriverProfile, password: string) => void;
}

const AddDriverModal = ({ onClose, onSubmit }: AddDriverModalProps) => {
  const [ firstName, setFirstName ] = useState("");
  const [ lastName, setLastName ] = useState("");
  const [ email, setEmail ] = useState("")
  const [ password, setPassword ] = useState("")
  const [ vehicle, setVehicle ] = useState("")


  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    const driver: DriverProfile = {
        id: 0, 
        email: email,
        firstName: firstName,
        lastName: lastName,
        role: "ROLE_DRIVER",
        vehicle: vehicle,
    };

    onSubmit(driver, password);
    onClose();
  };

  return (
    <div className={style.modal_overlay}>
      <div className={style.modal}>
        <h2>Add Driver</h2>
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            placeholder="First name"
            value={firstName}
            onChange={(e) => setFirstName(e.target.value)}
            required
          />
          <input
            type="text"
            placeholder="Last Name"
            value={lastName}
            onChange={(e) => setLastName(e.target.value)}
            required
          />
          <input
            type="text"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <input
            type="text"
            placeholder="Vehicle"
            value={vehicle}
            onChange={(e) => setVehicle(e.target.value)}
            required
          />
          <button type="submit">Save</button>
          <button type="button" onClick={onClose}>
            Cancel
          </button>
        </form>
      </div>
    </div>
  );
};

export default AddDriverModal;
