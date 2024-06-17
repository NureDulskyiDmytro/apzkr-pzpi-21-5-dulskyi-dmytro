package backend.security.repository;

import backend.security.model.Ordering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.ArrayList;

public interface OrderingRepository extends JpaRepository<Ordering, Long> {
   @Modifying
   @Query(value = "UPDATE ordering SET status = :status WHERE ordering_id = :orderingId", nativeQuery = true)
   @Transactional
   void updateOrdering(Long orderingId, Boolean status);

   @Query(value = "Select product_id, size From ordering Where ordering_id = :orderingId", nativeQuery = true)
   @Transactional
   ArrayList getOrderingData(Long orderingId);
}
