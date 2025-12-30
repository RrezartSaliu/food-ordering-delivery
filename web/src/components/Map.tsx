import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import type { LatLngExpression } from "leaflet";
import { useEffect } from "react";

interface Props {
    coords: LatLngExpression
}

export default function Map({coords}: Props) {
    
    useEffect(()=>{
        console.log(coords);
        
    })
  return (
    <MapContainer
      center={[42.13314,21.71542]} // Skopje example
      zoom={14}
      style={{ height: "50em", width: "80vh" }}
    >
      <TileLayer
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
      />
      <Marker position={coords}>
        <Popup>Driver location</Popup>
      </Marker>
    </MapContainer>
  );
}
