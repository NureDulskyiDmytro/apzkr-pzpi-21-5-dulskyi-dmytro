package backend.security.service;

import backend.security.model.Product;
import backend.security.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@Transactional
public class SmartDeviceService {
   @Autowired
   private final ProductRepository productRepository;

   public SmartDeviceService(ProductRepository productRepository) {
      this.productRepository = productRepository;
   }

   public void updateAllProductIndicators() {
      List<Product> products = productRepository.findAll();
      Random random = new Random();

      for (Product product : products) {
         String temperatureNow = String.valueOf(10 + random.nextInt(21)); // 10 to 30 degrees
         String humidityNow = String.valueOf(5 + random.nextInt(86)); // 5 to 90 percent
         product.setTemperatureNow(temperatureNow);
         product.setHumidityNow(humidityNow);
         productRepository.save(product);
      }
   }
}
