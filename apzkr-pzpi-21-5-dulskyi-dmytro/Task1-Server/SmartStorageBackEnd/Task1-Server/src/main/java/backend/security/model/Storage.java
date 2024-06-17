package backend.security.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "addressStorage")

public class Storage {
   @Id
   @Column(name = "address_storage_id")
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long addressStorageId;

   @Column(name = "storage_id", unique = true)
   @NotNull
   private String storageId;

   @Column(name = "size")
   private String size;

   @Column(name = "price")
   private Long price;

   @Column(name = "status")
   private Boolean status;

   @Column(name = "product_id")
   private Long productId;

   public Long getAddressStorageId() {
      return addressStorageId;
   }

   public void setAddressStorageId(Long addressStorageId) {
      this.addressStorageId = addressStorageId;
   }

   public String getStorageId() {
      return storageId;
   }

   public void setStorageId(String storageId) {
      this.storageId = storageId;
   }

   public String getSize() {
      return size;
   }

   public void setSize(String size) {
      this.size = size;
   }

   public Long getPrice() {
      return price;
   }

   public void setPrice(Long price) {
      this.price = price;
   }

   public Boolean getStatus() {
      return status;
   }

   public void setStatus(Boolean status) {
      this.status = status;
   }

   public Long getProductId() {
      return productId;
   }

   public void setProductId(Long productId) {
      this.productId = productId;
   }
}
