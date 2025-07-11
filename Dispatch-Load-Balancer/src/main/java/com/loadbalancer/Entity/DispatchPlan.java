package com.loadbalancer.Entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DispatchPlan {

    private String vehicleId;
    private double totalLoad;
    private double totalDistance;
    private List<Order> assignedOrders;
}
