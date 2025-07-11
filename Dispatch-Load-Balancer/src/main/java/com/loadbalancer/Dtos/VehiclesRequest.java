package com.loadbalancer.Dtos;

import com.loadbalancer.Entity.Vehicle;
import lombok.Data;
import java.util.List;

@Data
public class VehiclesRequest {
    private List<Vehicle> vehicles;

}

