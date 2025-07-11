package com.loadbalancer.Repository;

import com.loadbalancer.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {}

