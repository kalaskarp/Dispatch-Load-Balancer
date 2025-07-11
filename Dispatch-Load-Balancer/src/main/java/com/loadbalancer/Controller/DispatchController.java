package com.loadbalancer.Controller;

import com.loadbalancer.Dtos.OrdersRequest;
import com.loadbalancer.Dtos.VehiclesRequest;
import com.loadbalancer.Entity.Order;
import com.loadbalancer.Entity.Vehicle;
import com.loadbalancer.Service.DispatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("/api/dispatch")
@RequiredArgsConstructor
public class DispatchController {

    private final DispatchService dispatchService;

    @PostMapping("/orders")
    public ResponseEntity<?> saveOrders(@RequestBody OrdersRequest request) {
        dispatchService.saveOrders(request.getOrders());
        return ResponseEntity.ok(Map.of("message", "Delivery orders accepted.", "status", "success"));
    }

    @PostMapping("/vehicles")
    public ResponseEntity<?> saveVehicles(@RequestBody VehiclesRequest request) {
        dispatchService.saveVehicles(request.getVehicles());
        return ResponseEntity.ok(Map.of("message", "Vehicle details accepted.", "status", "success"));
    }

    @GetMapping("/plan")
    public ResponseEntity<?> getPlan() {
        return ResponseEntity.ok(Map.of("dispatchPlan", dispatchService.generatePlan()));
    }
}
