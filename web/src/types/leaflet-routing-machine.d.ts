import * as L from "leaflet";

declare module "leaflet" {
  namespace Routing {
    interface Control extends L.Control {
      setWaypoints(waypoints: L.LatLng[]): void;
    }

    function control(options: any): Control;
    function osrmv1(options?: any): any;
  }
}
