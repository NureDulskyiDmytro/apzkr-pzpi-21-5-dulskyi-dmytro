package backend.security.service;

import backend.security.model.Ordering;
import backend.security.repository.OrderingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderingService {
   @Autowired
   private final OrderingRepository orderingRepository;

   public OrderingService(OrderingRepository orderingRepository) {
      this.orderingRepository = orderingRepository;
   }

   public void save(Ordering ordering) {
      this.orderingRepository.save(ordering);
   }

   public Optional<Ordering> getOneOrdering(Long id) {
      return this.orderingRepository.findById(id);
   }

   public List<Ordering> getAllOrderings() {
      return this.orderingRepository.findAll();
   }

   public void deleteOneOrdering(Long id) {
      this.orderingRepository.deleteById(id);
   }

   public void updateOrdering(Ordering ordering, Long id) {
      Ordering orderingToChange = this.getOneOrdering(id).get();

      if (ordering.getDate() != null) {
         orderingToChange.setDate(ordering.getDate());
      }

      if (ordering.getStatus() != null) {
         orderingToChange.setStatus(ordering.getStatus());
      }

      if (ordering.getProductId() != null) {
         orderingToChange.setProductId(ordering.getProductId());
      }

      if (ordering.getSum() != null) {
         orderingToChange.setSum(ordering.getSum());
      }

      if (ordering.getSize() != null) {
         orderingToChange.setSize(ordering.getSize());
      }

      this.save(orderingToChange);
   }
}
