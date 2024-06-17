package backend.security.rest;

import backend.security.model.Product;
import backend.security.repository.ProductRepository;
import backend.security.service.ProductService;
import backend.security.service.SmartDeviceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class ProductController {
   @Autowired
   private ProductRepository productRepository;
   @Autowired
   private ProductService productService;
   @Autowired
   private SmartDeviceService smartDeviceService;

   public ProductController() {
   }

   @RequestMapping(value = "/addProduct", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   @SecurityRequirement(name = "bearerAuth")
   public ResponseEntity<Product> saveProduct(@RequestBody @Valid Product product) {
      HttpHeaders headers = new HttpHeaders();

      if (product == null) {
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

      productService.save(product);
      return new ResponseEntity<>(product, headers, HttpStatus.CREATED);
   }

   @RequestMapping(value = "/updateIndicators", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   @SecurityRequirement(name = "bearerAuth")
   public void updateAllProductIndicators() {
      smartDeviceService.updateAllProductIndicators();
   }

   @RequestMapping(value = "/updateProduct/{productId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   @SecurityRequirement(name = "bearerAuth")
   public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable Long productId) {
      HttpHeaders headers = new HttpHeaders();

      if (product == null) {
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

      productService.updateProduct(product, productId);

      return new ResponseEntity<>(product, headers, HttpStatus.OK);
   }

   @DeleteMapping("/deleteProduct/{productId}")
   @SecurityRequirement(name = "bearerAuth")
   public ResponseEntity<Product> deleteOneProduct(@PathVariable Long productId) {
      productService.deleteOneProduct(productId);
      return new ResponseEntity<>(HttpStatus.OK);
   }

   @GetMapping("/getProductById/{productId}")
   @SecurityRequirement(name = "bearerAuth")
   public ResponseEntity<Optional<Product>> getOneProduct(@PathVariable Long productId) {
      return ResponseEntity.ok(productService.getOneProduct(productId));
   }

   @GetMapping("/getProduct")
   @SecurityRequirement(name = "bearerAuth")
   public ResponseEntity<List<Product>> getAllProducts() {
      return ResponseEntity.ok(productService.getAllProducts());
   }

   @PutMapping("/updateIndicator/{productId}/{temperatureNow}/{humidityNow}/{workerId}")
   @SecurityRequirement(name = "bearerAuth")
   public ArrayList updateIndicator(@PathVariable Long productId, @PathVariable String temperatureNow,
                                    @PathVariable String humidityNow, @PathVariable String workerId) {
      productRepository.updateIndicator(productId, temperatureNow, humidityNow, workerId);
      return new ArrayList();
   }

   @PutMapping("/updateStorageAddress/{productId}/{storageAddress}")
   @SecurityRequirement(name = "bearerAuth")
   public ArrayList updateStorageAddress(@PathVariable Long productId, @PathVariable String storageAddress) {
      productRepository.updateStorageAddress(productId, storageAddress);
      return new ArrayList();
   }

   @PutMapping("/getProductId/{weight}/{size}/{typeOfProduct}/{quantity}/{storageLife}/{customerId}")
   @SecurityRequirement(name = "bearerAuth")
   public ArrayList getProductId(@PathVariable String weight, @PathVariable String size,
                                 @PathVariable String typeOfProduct, @PathVariable Long quantity,
                                 @PathVariable String storageLife, @PathVariable String customerId) {
      return productRepository.getProductId(weight, size, typeOfProduct, quantity, storageLife, customerId);
   }

   @PutMapping("/getProductByCustomerId/{customerId}")
   @SecurityRequirement(name = "bearerAuth")
   public ArrayList getProductByCustomerId(@PathVariable String customerId) {
      return productRepository.getProductByCustomerId(customerId);
   }

   @GetMapping("/getAnalise1ByArrayList")
   @SecurityRequirement(name = "bearerAuth")
   public ArrayList getAnalise1ByArrayList() {
      return productRepository.getAnalise1ByArrayList();
   }

   @GetMapping("/getAnalise2ByArrayList")
   @SecurityRequirement(name = "bearerAuth")
   public ArrayList getAnalise2ByArrayList() {
      return productRepository.getAnalise2ByArrayList();
   }

   @GetMapping("/getAnalise3ByArrayList")
   @SecurityRequirement(name = "bearerAuth")
   public ArrayList getAnalise3ByArrayList() {
      return productRepository.getAnalise3ByArrayList();
   }
}
