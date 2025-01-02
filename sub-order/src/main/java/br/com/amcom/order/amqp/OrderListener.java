package br.com.amcom.order.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.amcom.order.dto.OrderDTO;
import br.com.amcom.order.models.Order;
import br.com.amcom.order.services.OrderService;


@Component
public class OrderListener {
	
	@Autowired
	private OrderService orderService;
	
	@Transactional
	@RabbitListener(queues = "process-order")
	public void receiveMessage(OrderDTO orderDTO) {
		Order order = new Order();
		order.setClient(orderDTO.getClient());
		order.setOrderNumber(orderDTO.getOrderNumber());
		order.setOrderDate(orderDTO.getOrderDate());
		order.setOrderValue(orderDTO.getOrderValue());
		order.setProducts(orderDTO.getProducts());
		orderService.createOrder(order);
		System.out.println("Order para o cliente : " + order.getClient() + " incluída com sucesso no banco de dados.");
	}
	
}
