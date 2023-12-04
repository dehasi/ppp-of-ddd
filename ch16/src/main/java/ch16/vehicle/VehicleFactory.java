package ch16.vehicle;

import java.util.UUID;

// Factory used for example - not mandatory
class VehicleFactory {
    static Vehicle CreateVehicle() {
        var id = UUID.randomUUID();
        return new Vehicle(id);
    }
}
