package br.com.amcom.order.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import br.com.amcom.order.dto.ProductDTO;
import br.com.amcom.order.models.Product;
import br.com.amcom.order.services.ProductService;


@RestController
@RequestMapping(value = "/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> listProductById(@PathVariable String id) {
			Product product = productService.findProductById(Long.parseLong(id));
			return ResponseEntity.ok().body(product);
	}
	
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductDTO>> listAllProduct() {
		List<Product> products = productService.findAllProduct();
		List<ProductDTO> productDTO = products.stream().map(product -> new ProductDTO(product)).collect(Collectors.toList());
		return ResponseEntity.ok().body(productDTO);
	}
	
	
	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createProduct(@RequestBody Product product){
		productService.createProduct(product);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(product.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	
	@PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updateProduct(@RequestBody Product product, @PathVariable Long id) {
		product.setId(id);
		product = productService.updateProduct(product);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<ProductDTO>> listAllProductPage( @RequestParam(value = "page"		, defaultValue="0")    Integer page, 
																@RequestParam(value = "linesPerPage", defaultValue="24")   Integer size, 
																@RequestParam(value = "sortBy"		, defaultValue="name") String  sortBy, 
																@RequestParam(value = "ascending"   , defaultValue="true") boolean ascending) {
		Page<Product> products = productService.findProductPage(page, size, sortBy, ascending);
		Page<ProductDTO> productDTO = products.map(product -> new ProductDTO(product));
		return ResponseEntity.ok().body(productDTO);
	}
	
}
