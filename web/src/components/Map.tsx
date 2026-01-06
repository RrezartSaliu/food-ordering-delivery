import { MapContainer, TileLayer, Marker, Popup, useMap } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import type { LatLngExpression } from "leaflet";
import { useEffect, useRef } from "react";
import "leaflet-routing-machine";
import "leaflet-routing-machine/dist/leaflet-routing-machine.css";
import L from "leaflet";


interface Props {
    coords: LatLngExpression
    driverCoords: LatLngExpression|null
}

function Routing({
  from,
  to,
}: {
  from: LatLngExpression;
  to: LatLngExpression;
}) {
  const map = useMap();
  const routingRef = useRef<L.Routing.Control | null>(null);

  useEffect(() => {
    if (routingRef.current) return;

    routingRef.current = L.Routing.control({
      waypoints: [L.latLng(from), L.latLng(to)],
      addWaypoints: false,
      draggableWaypoints: false,
      fitSelectedRoutes: true,
      show: false,
      lineOptions: {
        styles: [{ weight: 5 }],
      },
      router: L.Routing.osrmv1({
        serviceUrl: "https://router.project-osrm.org/route/v1",
      }),
    }).addTo(map);

    return () => {
      // IMPORTANT: remove via control itself
      routingRef.current?.remove();
      routingRef.current = null;
    };
  }, [map]);

  // 🔄 update route when driver moves
  useEffect(() => {
    routingRef.current?.setWaypoints([
      L.latLng(from),
      L.latLng(to),
    ]);
  }, [from, to]);

  return null;
}


export default function Map({coords, driverCoords}: Props) {
    
    useEffect(()=>{
        console.log("order coords:",coords);
        console.log("driver coords:", driverCoords)
    }, [driverCoords])
  return (
    <MapContainer
      center={[42.13314,21.71542]}
      zoom={14}
      style={{ height: "50em", width: "80vh" }}
    >
      <TileLayer
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
      />
      <Marker position={coords}>
        <Popup>Order location</Popup>
      </Marker>
      {
        driverCoords && (
          <>
          <Marker position={driverCoords}>
            <Popup>Driver Location</Popup>
          </Marker>
          <Routing from={driverCoords} to={coords} />
          </>
        )
      }
    </MapContainer>
  );
}
