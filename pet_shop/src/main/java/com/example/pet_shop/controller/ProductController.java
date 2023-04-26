package com.example.pet_shop.controller;
import com.example.pet_shop.model.DTOS.orderDTO.CartDTO;
import com.example.pet_shop.model.DTOS.productDTOs.*;
import com.example.pet_shop.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController extends AbstractController {
    @Autowired
    protected LoginManager loginManager;
    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ProductInfoDTO viewProductById(@PathVariable int id){
        return productService.viewProductById(id);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ProductInfoDTO>> viewAllProducts(Pageable pageable){
        Page<ProductInfoDTO> productPage = productService.viewAll(pageable);
        return ResponseEntity.ok(productPage);
    }

    @GetMapping("/filter/{subcategory-id}")
    public Page<ProductInfoDTO> filter(@PathVariable("subcategory-id") int subId,
                                       @RequestParam(value = "order", defaultValue = "asc") String order,
                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.filter(subId, pageable, order);
    }


    @GetMapping("/search")
    public Page<ProductInfoDTO> search(@RequestParam(value = "name") String name,
                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.search(name, pageable);
    }

    @PostMapping
    public ProductInfoDTO addProduct(@RequestBody ProductAddDTO dto){
        checkAuthorization(loginManager);
        return productService.addProduct(dto);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<?> editProduct(@RequestBody ProductAddDTO productDto, @PathVariable int id) {
        checkAuthorization(loginManager);
        productService.editProduct(productDto, id);
        return ResponseEntity.ok("Changes edited successfully.");
    }

    @PutMapping("/{productId}")
    public void addToCart(@PathVariable int productId, HttpSession session) {
        CartDTO cart = getCart(session);
        productService.addToCart(productId,cart);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeFromCart(@PathVariable int productId, HttpSession session){
        CartDTO cart = getCart(session);
        productService.removeFromCart(productId, cart);
        return ResponseEntity.ok("Product removed from cart");
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteProduct(@PathVariable int id){
        checkAuthorization(loginManager);
        productService.deleteProduct(id);
        return ResponseEntity.ok().body("Product deleted !");
    }

}
