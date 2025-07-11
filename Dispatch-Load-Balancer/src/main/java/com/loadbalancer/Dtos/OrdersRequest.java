package com.loadbalancer.Dtos;

import com.loadbalancer.Entity.Order;
import lombok.Data;

import java.util.List;

@Data
public class OrdersRequest {
    private List<Order> orders;

}
