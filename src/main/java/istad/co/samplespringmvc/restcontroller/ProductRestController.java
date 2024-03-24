package istad.co.samplespringmvc.restcontroller;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import istad.co.samplespringmvc.dto.ProductRequest;
import istad.co.samplespringmvc.dto.ProductResponse;
import istad.co.samplespringmvc.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductRestController {
    private final ProductService productService;
    private Map<String, Object> response(Object object, String message, int status){
        HashMap<String, Object> response = new HashMap<>();
        response.put("payload",object);
        response.put("message", message);
        response.put("status", status);
        return response;
    }
  // ?productName=fanta
    @GetMapping("/get-all")
    public Map<String, Object> getAllProducts(
            @RequestParam(defaultValue = "" ) String productName) {
        return response(
                productService.getAllProduct(productName),
                "Successfully Retrieved all data!",
                HttpStatus.OK.value()
        );

    }
    @PostMapping("/new-product")
    public Map<String, Object> createNewProduct(@RequestBody ProductRequest request) {
        return response(
                productService.createProduct(request),
                "Created New Product Successfully!",
                HttpStatus.CREATED.value());
    }
    // website-name/product/12 -> pathvariables
    // website-name/product?name=sth&&cate=3 -> requestparams
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Map<String,Object> findProductByID(@PathVariable int id){
        return response(
                productService.findProductByID(id),
                "Successfully Retrieved the record!",
                HttpStatus.FOUND.value());
    }

//    delete the product
    @PatchMapping("/{id}")
    public Map<String, Object> updateProduct(@PathVariable int id , @RequestBody ProductRequest request){
        return response(
                productService.updateProduct(id,request),
                "Update Product Successfully",
                HttpStatus.OK.value()
        );
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> deleteProduct(@PathVariable int id){
        productService.deleteProduct(id);
        return response(new ArrayList<>(),"Delete Successfully",HttpStatus.OK.value());
    }
}
