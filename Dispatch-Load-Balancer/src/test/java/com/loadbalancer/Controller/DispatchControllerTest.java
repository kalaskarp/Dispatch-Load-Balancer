package com.loadbalancer.Controller;

import com.loadbalancer.Dtos.OrdersRequest;
import com.loadbalancer.Dtos.VehiclesRequest;
import com.loadbalancer.Entity.DispatchPlan;
import com.loadbalancer.Entity.Order;
import com.loadbalancer.Entity.Vehicle;
import com.loadbalancer.Service.DispatchService;
import com.loadbalancer.models.Priority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@org.junit.jupiter.api.extension.ExtendWith(SpringExtension.class)
public class DispatchControllerTest {

    @Mock
    private DispatchService dispatchService;

    @InjectMocks
    private DispatchController dispatchController;

    private Order sampleOrder;
    private Vehicle sampleVehicle;

    @BeforeEach
    public void setup() {
        sampleOrder = new Order();
        sampleOrder.setOrderId("ORD001");
        sampleOrder.setAddress("Delhi");
        sampleOrder.setLatitude(28.61);
        sampleOrder.setLongitude(77.20);
        sampleOrder.setPackageWeight(10);
        sampleOrder.setPriority(Priority.HIGH);

        sampleVehicle = new Vehicle();
        sampleVehicle.setVehicleId("VEH001");
        sampleVehicle.setCapacity(100);
        sampleVehicle.setCurrentAddress("Delhi");
        sampleVehicle.setCurrentLatitude(28.61);
        sampleVehicle.setCurrentLongitude(77.20);
    }

    @Test
    public void testSaveOrders() {
        OrdersRequest request = new OrdersRequest();
        request.setOrders(Arrays.asList(sampleOrder));

        ResponseEntity<?> response = dispatchController.saveOrders(request);

        verify(dispatchService, times(1)).saveOrders(request.getOrders());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Delivery orders accepted.", ((Map<?, ?>) response.getBody()).get("message"));
    }

    @Test
    public void testSaveVehicles() {
        VehiclesRequest request = new VehiclesRequest();
        request.setVehicles(Arrays.asList(sampleVehicle));

        ResponseEntity<?> response = dispatchController.saveVehicles(request);

        verify(dispatchService, times(1)).saveVehicles(request.getVehicles());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Vehicle details accepted.", ((Map<?, ?>) response.getBody()).get("message"));
    }

    @Test
    public void testGetPlan() {
        DispatchPlan plan = new DispatchPlan("VEH001", 10, 5.0, List.of(sampleOrder));

        when(dispatchService.generatePlan()).thenReturn(Arrays.asList(plan));

        ResponseEntity<?> response = dispatchController.getPlan();

        verify(dispatchService, times(1)).generatePlan();
        assertEquals(200, response.getStatusCodeValue());
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertNotNull(body.get("dispatchPlan"));
    }
}
