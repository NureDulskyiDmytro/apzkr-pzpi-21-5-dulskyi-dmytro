package backend.security.repository;

import backend.security.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
   @Query(value = "Select summa, size, type_of_product, storage_life, quantity, product_id From product  Order By product.size, product.summa Desc", nativeQuery = true)
   @Transactional
   ArrayList getAnalise1ByArrayList();

   @Query(value = "Select sum(summa) as summa, size, sum(quantity) From product  Group By size Order By summa Desc", nativeQuery = true)
   @Transactional
   ArrayList getAnalise2ByArrayList();

   @Query(value = "Select sum(summa) as summa, type_of_product, sum(quantity) From product  Group By type_of_product Order By summa Desc", nativeQuery = true)
   @Transactional
   ArrayList getAnalise3ByArrayList();

   @Modifying
   @Query(value = "UPDATE product SET temperature_now = :temperatureNow, humidity_now = :humidityNow, worker_id = :workerId WHERE product_id = :productId", nativeQuery = true)
   @Transactional
   void updateIndicator(Long productId, String temperatureNow, String humidityNow, String workerId);

   @Modifying
   @Query(value = "UPDATE product SET storage_address = :storageAddress WHERE product_id = :productId", nativeQuery = true)
   @Transactional
   void updateStorageAddress(Long productId, String storageAddress);

   @Query(value = "Select product_id From product Where weight = :weight and size = :size and type_of_product = :typeOfProduct and " +
      "quantity = :quantity and storage_life = :storageLife and customer_id = :customerId", nativeQuery = true)
   @Transactional
   ArrayList getProductId(String weight, String size, String typeOfProduct, Long quantity, String storageLife, String customerId);

   @Query(value = "Select * From product Where customer_id = :customerId", nativeQuery = true)
   @Transactional
   ArrayList getProductByCustomerId(String customerId);
}
