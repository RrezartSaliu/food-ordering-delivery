import { useApi } from "../hooks/useApi";
import { useAuth } from "../util/AuthProvider";
import type { DriverProfile } from "../types/Profile";
import { useState } from "react";
import AddDriverModal from "../components/AddDriverModal";

const DriverManagementPage = () => {
  const { token } = useAuth();
  const addDriverApi = useApi<String>(
    `${import.meta.env.VITE_API_URL}auth/driver/register-driver-user`,
    token
  );
  const [driverToast, setDriverToast] = useState<string | null>(null);
  const [isAddDriverOpen, setIsAddDriverOpen] = useState(false);

  const handleAddDriver = async (driver: DriverProfile, password: string) => {
    console.log("Add driver:", driver, password);
    const res = await addDriverApi.post("", {
      email: driver.email,
      firstName: driver.firstName,
      lastName: driver.lastName,
      password: password,
      vehicle: driver.vehicle,
    });

    if (res?.success) {
      setDriverToast("Driver added successfully!");

      setTimeout(() => setDriverToast(null), 4000);
    }
  };

  return (
    <div>
      driver management page
      <button onClick={() => setIsAddDriverOpen(true)}>add driver</button>
      {driverToast && <div className="toast success-toast">{driverToast}</div>}
      {isAddDriverOpen && (
        <AddDriverModal
          onClose={() => setIsAddDriverOpen(false)}
          onSubmit={handleAddDriver}
        />
      )}
    </div>
  );
};

export default DriverManagementPage;
