package br.com.amcom.order.services;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.amcom.order.custom.exceptions.DataIntegrityException;
import br.com.amcom.order.custom.exceptions.ObjectNotFoundException;
import br.com.amcom.order.models.Order;
import br.com.amcom.order.models.Product;
import br.com.amcom.order.repositories.OrderRepository;

@Service
public class OrderService {
	
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	
	public Order findOrderById(Long id) {
		return orderRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Order not found! Id: " + id + ", Type : " + Order.class.getName()));
	}



	public List<Order> findAllOrder() {
		List<Order> orders = orderRepository.findAll();
		if(orders.isEmpty())
			throw new ObjectNotFoundException("No orders found " + ", Type : " + Order.class.getName());
		
		return orders;
	}

	public Order createOrder(Order order) {
		Order orderSaved = orderRepository.save(order);
		if(orderSaved == null)
			throw new ObjectNotFoundException("Error when saving order " + ", Type : " + Order.class.getName());
		
		return orderSaved;
	}
	
	public void deleteOrder(Long id) {
		findOrderById(id);
		try {
			orderRepository.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Delete not allow for this order.");
		}	
	}
	
	
	public BigDecimal caculateTotalOrder(Order order) {
		List<Product> products = order.getProducts();
		Double orderValue = 0D;
		for(Product product: products) {
			orderValue = (product.getValue().doubleValue() * product.getQuantity()) + orderValue;
		}
		return new BigDecimal(orderValue);
	}
	
	public String dateOrder() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
	}
	
	public Page<Order> findOrderPage(Integer page, Integer size, String sortBy, boolean ascending){
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
		return orderRepository.findAll(pageable);
	}

}
