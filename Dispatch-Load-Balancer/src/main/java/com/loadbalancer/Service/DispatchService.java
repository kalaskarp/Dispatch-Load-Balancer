package com.loadbalancer.Service;

import com.loadbalancer.Entity.DispatchPlan;
import com.loadbalancer.Entity.Order;
import com.loadbalancer.Entity.Vehicle;
import com.loadbalancer.Repository.OrderRepository;
import com.loadbalancer.Repository.VehicleRepository;
import com.loadbalancer.Utils.DistanceUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
@AllArgsConstructor
public class DispatchService {

    private final OrderRepository orderRepo;
    private final VehicleRepository vehicleRepo;

    public void saveOrders(List<Order> orders) {
        orderRepo.saveAll(orders);
    }

    public void saveVehicles(List<Vehicle> vehicles) {
        vehicleRepo.saveAll(vehicles);
    }

    public List<DispatchPlan> generatePlan() {
        List<Order> orders = orderRepo.findAll();
        List<Vehicle> vehicles = vehicleRepo.findAll();

        orders.sort(Comparator.comparing(Order::getPriority));

        Map<String, List<Order>> vehicleAssignments = new HashMap<>();
        for (Vehicle v : vehicles) {
            vehicleAssignments.put(v.getVehicleId(), new ArrayList<>());
        }


        for (Order o : orders) {
            Vehicle closestVehicle = null;
            double minDist = Double.MAX_VALUE;

            for (Vehicle v : vehicles) {
                double usedCapacity = vehicleAssignments.get(v.getVehicleId())
                        .stream().mapToDouble(Order::getPackageWeight).sum();

                if ((usedCapacity + o.getPackageWeight()) <= v.getCapacity()) {
                    double dist =  DistanceUtil.haversine(v.getCurrentLatitude(), v.getCurrentLongitude(),
                            o.getLatitude(), o.getLongitude());

                    if (dist < minDist) {
                        minDist = dist;
                        closestVehicle = v;
                    }
                }
            }

            if (closestVehicle != null) {
                vehicleAssignments.get(closestVehicle.getVehicleId()).add(o);
            }
        }

        List<DispatchPlan> plans = new ArrayList<>();
        for (Vehicle v : vehicles) {
            List<Order> assignedOrders = vehicleAssignments.get(v.getVehicleId());
            double totalLoad = assignedOrders.stream().mapToDouble(Order::getPackageWeight).sum();
            double totalDistance = assignedOrders.stream()
                    .mapToDouble(o -> DistanceUtil.haversine(v.getCurrentLatitude(), v.getCurrentLongitude(),
                            o.getLatitude(), o.getLongitude()))
                    .sum();

            plans.add(new DispatchPlan(v.getVehicleId(), totalLoad, totalDistance, assignedOrders));
        }

        return plans;
    }
}
