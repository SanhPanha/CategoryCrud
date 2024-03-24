package istad.co.samplespringmvc.service.serviceImpl;

import istad.co.samplespringmvc.dto.CategoryResponse;
import istad.co.samplespringmvc.dto.ProductRequest;
import istad.co.samplespringmvc.dto.ProductResponse;
import istad.co.samplespringmvc.model.Product;
import istad.co.samplespringmvc.repository.CategoryRepository;
import istad.co.samplespringmvc.repository.ProductRepository;
import istad.co.samplespringmvc.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final  ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private Product searchProductByID(int id){
       return  productRepository.getAllProducts()
                .stream().filter(p->p.getId()==id)
                .findFirst()
                .orElseThrow(()->new HttpClientErrorException(HttpStatus.NOT_FOUND,"Product doesn't exist!!"));
    }
    private ProductResponse mapProductToResponse(Product product){
        var category = categoryRepository.getAllCategories().stream()
                .filter(cate -> cate.getId()==product.getCategoryId())
                .findFirst()
                .orElseThrow(()->new HttpClientErrorException(HttpStatus.NOT_FOUND,"Category doesn't exist!!"));

        var categoryResponse = CategoryResponse.builder()
                .id(category.getId())
                .title(category.getTitle())
                .description(category.getDescription())
                .build();


        return ProductResponse.builder()
                .id(product.getId())
                .imageUrl(product.getImageUrl())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .categoryResponse(categoryResponse)
                .build();
    }
    private Product mapRequestToProduct(ProductRequest request){
//        prevent null if the request fields are null
        return Product.builder()
                .title(request.title())
                .price(request.price())
                .imageUrl(request.imageUrl())
                .description(request.description())
                .categoryId(request.categoryId())
                .build();
    }
    @Override
    public List<ProductResponse> getAllProduct(String productName) {
      var product = productRepository.getAllProducts();
      if (!productName.isEmpty()){
          product = product.stream().filter(
                  pro-> pro.getTitle().toLowerCase().contains(productName.toLowerCase())
          ).toList();
      }
      return  product
              .stream()
              .map(this::mapProductToResponse).toList();

    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        Product newProduct = mapRequestToProduct(request);
        var maxID = productRepository.getAllProducts()
                        .stream()
                .max(Comparator.comparingInt(Product::getId))
                .map(Product::getId);
        int newID=1;
        if(maxID.isPresent()) {
             newID = maxID.get() + 1;
        }
        newProduct.setId(newID);
        productRepository.addProduct(newProduct);

        return mapProductToResponse(newProduct);

    }

    @Override
    public ProductResponse findProductByID(int id) {
        return mapProductToResponse(searchProductByID(id));
    }


    @Override
    public void deleteProduct(int productId) {
        productRepository.deleteProduct(searchProductByID(productId).getId());

    }

    @Override
    public ProductResponse updateProduct(int id , ProductRequest productRequest) {
        // find if the product exist
        var result = searchProductByID(id);
        result= mapRequestToProduct(productRequest);
        result.setId(id);
        productRepository.updateProduct(result);
        return mapProductToResponse(result);
    }
}
