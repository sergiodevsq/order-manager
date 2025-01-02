package br.com.amcom.order.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import br.com.amcom.order.models.Order;
import br.com.amcom.order.models.Product;

public class OrderDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	private String client;
	private String orderNumber;
	private String orderDate;
	private BigDecimal orderValue;
	
	private List<Product> products;
	
	public OrderDTO() {}
	
	public OrderDTO(Order order) {
		
		this.client = order.getClient();
		this.orderNumber = order.getOrderNumber();
		this.orderDate = order.getOrderDate();
		this.orderValue = order.getOrderValue();
		this.products = order.getProducts();
	}


	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public BigDecimal getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(BigDecimal orderValue) {
		this.orderValue = orderValue;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}


	
	
	
}
