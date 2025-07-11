package com.loadbalancer.Entity;

import com.loadbalancer.models.Priority;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    private String orderId;
    private double latitude;
    private double longitude;
    private String address;
    private double packageWeight;

    @Enumerated(EnumType.STRING)
    private Priority priority;
}
