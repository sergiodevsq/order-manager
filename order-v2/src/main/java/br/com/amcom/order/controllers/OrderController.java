package br.com.amcom.order.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.amcom.order.dto.OrderDTO;
import br.com.amcom.order.models.Order;
import br.com.amcom.order.services.OrderService;
import br.com.amcom.order.services.ProductService;


@RestController
@RequestMapping(value = "/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Order> listOrderById(@PathVariable String id) {
		Order order = orderService.findOrderById(Long.parseLong(id));
		return ResponseEntity.ok().body(order);
	}
	
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OrderDTO>> listAllOrder() {
		List<Order> orders = orderService.findAllOrder();
		List<OrderDTO> orderDTO = orders.stream().map(order -> new OrderDTO(order)).collect(Collectors.toList());
		return ResponseEntity.ok().body(orderDTO);
	}
	
	@Transactional
	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createOrder(@RequestBody Order order) {
		//List<Long> productsId = productService.validateProductsInOrder(order.getProducts());
		productService.validateProductsInOrder(order.getProducts());
		order.setOrderValue(orderService.caculateTotalOrder(order));
		order.setOrderNumber(String.valueOf(System.currentTimeMillis()));
		order.setOrderDate(orderService.dateOrder());
		OrderDTO orderDTO = new OrderDTO(order);
		//Message message = new Message(("criei uma order com o ID : " + order.getClient()).getBytes());
		//rabbitTemplate.send("process-order", message);
		rabbitTemplate.convertAndSend("process-order", orderDTO);
		
		//orderService.createOrder(order);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(order.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping()
	public Order updateOrder(Order order) {
		
		return null;
	}
	
	@DeleteMapping(value = "delete/{id}")
	public ResponseEntity<Void> deleteOrderById(@PathVariable(name = "id") Long id) {
		orderService.deleteOrder(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<OrderDTO>> listAllOrderPage( @RequestParam(value = "page"		, defaultValue="0")    Integer page, 
															@RequestParam(value = "linesPerPage", defaultValue="24")   Integer size, 
															@RequestParam(value = "sortBy"		, defaultValue="client") String  sortBy, 
															@RequestParam(value = "ascending"   , defaultValue="true") boolean ascending) {
		Page<Order> orders = orderService.findOrderPage(page, size, sortBy, ascending);
		Page<OrderDTO> orderDTO = orders.map(order -> new OrderDTO(order));
		return ResponseEntity.ok().body(orderDTO);
	}
	
	
}
