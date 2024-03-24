package istad.co.samplespringmvc.service;

import istad.co.samplespringmvc.dto.ProductRequest;
import istad.co.samplespringmvc.dto.ProductResponse;
import istad.co.samplespringmvc.model.Product;

import java.util.List;
public interface ProductService {
    List<ProductResponse> getAllProduct(String productName);
    ProductResponse createProduct(ProductRequest productRequest);
    ProductResponse findProductByID(int id );
    void deleteProduct(int productId);
    ProductResponse updateProduct(int id ,  ProductRequest productRequest);
}
