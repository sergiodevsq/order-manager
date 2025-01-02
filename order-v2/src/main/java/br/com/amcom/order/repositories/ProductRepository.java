package br.com.amcom.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.amcom.order.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {}
