package backend.security.service;

import backend.security.model.Product;
import backend.security.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import java.util.List;
import java.util.Random;

import backend.security.model.Product;
import backend.security.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductService {
   @Autowired
   private final ProductRepository productRepository;

   public ProductService(ProductRepository productRepository) {
      this.productRepository = productRepository;
   }

   public void save(Product product) {
      this.productRepository.save(product);
   }

   public Optional<Product> getOneProduct(Long id) {
      return this.productRepository.findById(id);
   }

   public List<Product> getAllProducts() {
      return this.productRepository.findAll();
   }

   public void deleteOneProduct(Long id) {
      this.productRepository.deleteById(id);
   }

   public void updateProduct(Product product, Long id) {
      Product productToChange = this.getOneProduct(id).get();

      if (product.getWeight() != null) {
         productToChange.setWeight(product.getWeight());
      }

      if (product.getSize() != null) {
         productToChange.setSize(product.getSize());
      }

      if (product.getTypeOfProduct() != null) {
         productToChange.setTypeOfProduct(product.getTypeOfProduct());
      }

      if (product.getQuantity() != null) {
         productToChange.setQuantity(product.getQuantity());
      }

      if (product.getStorageLife() != null) {
         productToChange.setStorageLife(product.getStorageLife());
      }

      if (product.getStorageAddress() != null) {
         productToChange.setStorageAddress(product.getStorageAddress());
      }

      if (product.getTemperatureRange() != null) {
         productToChange.setTemperatureRange(product.getTemperatureRange());
      }

      if (product.getHumidityRange() != null) {
         productToChange.setHumidityRange(product.getHumidityRange());
      }

      if (product.getTemperatureNow() != null) {
         productToChange.setTemperatureNow(product.getTemperatureNow());
      }

      if (product.getHumidityNow() != null) {
         productToChange.setHumidityNow(product.getHumidityNow());
      }

      if (product.getCustomerId() != null) {
         productToChange.setCustomerId(product.getCustomerId());
      }

      if (product.getWorkerId() != null) {
         productToChange.setWorkerId(product.getWorkerId());
      }

      if (product.getSumma() != null) {
         productToChange.setSumma(product.getSumma());
      }

      this.save(productToChange);
   }
}
