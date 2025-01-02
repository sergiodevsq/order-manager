package br.com.amcom.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.amcom.order.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {}
