import type { RestaurantProfile as RestaurantPofileType } from "../types/Profile";

interface Prop {
  profile: RestaurantPofileType;
}


const RestaurantProfile = ({ profile }: Prop) => {
  return (
    <div id="home-container">
      <h1>Restaurant Profile</h1>
      <p>id: {profile.id}</p>
      <p>email: {profile.email}</p>
      <p>name: {profile.name}</p>
      <p>address: {profile.address}</p>
    </div>
  );
};

export default RestaurantProfile;