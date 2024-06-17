package backend.security.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "product")

public class Product {
   @Id
   @Column(name = "product_id")
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long productId;

   @Column(name = "weight", length = 50)
   @Size(max = 50)
   private String weight;

   @Column(name = "size", length = 50)
   private String size;

   @Column(name = "type_of_product", length = 100)
   private String typeOfProduct;

   @Column(name = "quantity", length = 50)
   private Long quantity;

   @Column(name = "storage_life", length = 50)
   private String storageLife;

   @Column(name = "storage_address", length = 50)
   private String storageAddress;

   @Column(name = "temperature_range", length = 50)
   private String temperatureRange;

   @Column(name = "humidity_range", length = 50)
   private String humidityRange;

   @Column(name = "temperature_now", length = 50)
   private String temperatureNow;

   @Column(name = "humidity_now", length = 50)
   private String humidityNow;

   @Column(name = "customer_id", length = 50)
   private String customerId;

   @Column(name = "worker_id", length = 50)
   private String workerId;

   @Column(name = "summa", length = 50)
   private Long summa;

   public Product() {
   }

   public Long getProductId() {
      return productId;
   }

   public void setProductId(Long productId) {
      this.productId = productId;
   }

   public String getWeight() {
      return weight;
   }

   public void setWeight(String weight) {
      this.weight = weight;
   }

   public String getSize() {
      return size;
   }

   public void setSize(String size) {
      this.size = size;
   }

   public String getTypeOfProduct() {
      return typeOfProduct;
   }

   public void setTypeOfProduct(String typeOfProduct) {
      this.typeOfProduct = typeOfProduct;
   }

   public Long getQuantity() {
      return quantity;
   }

   public void setQuantity(Long quantity) {
      this.quantity = quantity;
   }

   public String getStorageLife() {
      return storageLife;
   }

   public void setStorageLife(String storageLife) {
      this.storageLife = storageLife;
   }

   public String getStorageAddress() {
      return storageAddress;
   }

   public void setStorageAddress(String storageAddress) {
      this.storageAddress = storageAddress;
   }

   public String getTemperatureRange() {
      return temperatureRange;
   }

   public void setTemperatureRange(String temperatureRange) {
      this.temperatureRange = temperatureRange;
   }

   public String getHumidityRange() {
      return humidityRange;
   }

   public void setHumidityRange(String humidityRange) {
      this.humidityRange = humidityRange;
   }

   public String getTemperatureNow() {
      return temperatureNow;
   }

   public void setTemperatureNow(String temperatureNow) {
      this.temperatureNow = temperatureNow;
   }

   public String getHumidityNow() {
      return humidityNow;
   }

   public void setHumidityNow(String humidityNow) {
      this.humidityNow = humidityNow;
   }

   public String getCustomerId() {
      return customerId;
   }

   public void setCustomerId(String customerId) {
      this.customerId = customerId;
   }

   public String getWorkerId() {
      return workerId;
   }

   public void setWorkerId(String workerId) {
      this.workerId = workerId;
   }

   public Long getSumma() {
      return summa;
   }

   public void setSumma(Long summa) {
      this.summa = summa;
   }
}
