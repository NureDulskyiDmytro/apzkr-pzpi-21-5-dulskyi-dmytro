package backend.security.model;

import javax.persistence.*;


@Entity
@Table(name = "ordering")
public class Ordering {
   @Id
   @Column(name = "ordering_id")
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long orderingId;

   @Column(name = "date", length = 50)
   private String date;

   @Column(name = "status")
   private Boolean status;

   @Column(name = "product_id")
   private Long productId;

   @Column(name = "sum")
   private String sum;

   @Column(name = "size", length = 50)
   private String size;

   public Long getOrderingId() {
      return orderingId;
   }

   public void setOrderingId(Long orderingId) {
      this.orderingId = orderingId;
   }

   public String getDate() {
      return date;
   }

   public void setDate(String date) {
      this.date = date;
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

   public String getSum() {
      return sum;
   }

   public void setSum(String sum) {
      this.sum = sum;
   }

   public String getSize() {
      return size;
   }

   public void setSize(String size) {
      this.size = size;
   }
}
