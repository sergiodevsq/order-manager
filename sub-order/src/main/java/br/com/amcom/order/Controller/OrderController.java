package br.com.amcom.order.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value = "/input/order")
public class OrderController {

	
	@PostMapping(value = "/")
	public void readOrder() {
		
	}
	
	
}
