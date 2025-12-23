export interface BaseProfile {
    id: number;
    email: string;
    role: "ROLE_USER" | "ROLE_RESTAURANT" | "ROLE_DRIVER" | "ROLE_ADMIN";
}

export interface CustomerProfile extends BaseProfile {
    role: "ROLE_USER";
    firstName: string;
    lastName: string;
}

export interface RestaurantProfile extends BaseProfile {
    role: "ROLE_RESTAURANT";
    name: string;
    address: string;
    verified: boolean;
}

export interface DriverProfile extends BaseProfile {
    role: "ROLE_DRIVER";
    vehicle: string;
}

export interface AdminProfile extends BaseProfile {
    role: "ROLE_ADMIN";
    username: string
}

export type Profile =
    | CustomerProfile
    | RestaurantProfile
    | DriverProfile
    | AdminProfile;