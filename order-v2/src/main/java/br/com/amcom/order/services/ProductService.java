package br.com.amcom.order.services;


import java.util.ArrayList;
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

import br.com.amcom.order.models.Product;
import br.com.amcom.order.repositories.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	
	
	public Product findProductById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Product not found! Id: " + id + ", Type : " + Product.class.getName()));
	}
	
	
	public List<Product> findAllProduct(){
		List<Product> products = productRepository.findAll();
		if(products.isEmpty())
			throw new ObjectNotFoundException("No products found " + ", Type : " + Product.class.getName());
		
		return products;
	}
	
	public Product createProduct(Product product) {
		Product productSaved = productRepository.save(product);
		if(productSaved == null)
			throw new ObjectNotFoundException("Error when saving product " + ", Type : " + Product.class.getName());
		
		return productSaved;
	}


	public Product updateProduct(Product product) {
		findProductById(product.getId());
		return productRepository.save(product);
	}


	public void deleteProduct(Long id) {
		findProductById(id);
		try {
			productRepository.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Delete not allow for this product.");
		}	
	}
	
	public Page<Product> findProductPage(Integer page, Integer size, String sortBy, boolean ascending){
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
		return productRepository.findAll(pageable);
	}
	
	public List<Long> validateProductsInOrder(List<Product> products) throws ObjectNotFoundException {
		if(products.isEmpty())
			throw new ObjectNotFoundException("An order must have at least one product. " + ", Type : " + Product.class.getName());
		
		List<Long> productsId = new ArrayList<>();
		for(Product product: products) {
			findProductById(product.getId());
			productsId.add(product.getId());
		}
		return productsId;
	}
	

}
