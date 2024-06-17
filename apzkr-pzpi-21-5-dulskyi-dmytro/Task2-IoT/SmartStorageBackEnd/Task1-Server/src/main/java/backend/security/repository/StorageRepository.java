package backend.security.repository;

import backend.security.model.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.ArrayList;

public interface StorageRepository extends JpaRepository<Storage, Long> {
   @Modifying
   @Query(value = "UPDATE address_Storage SET status = :status, product_id = :productId WHERE address_storage_Id = :addressStorageId", nativeQuery = true)
   @Transactional
   void updateStorage(Long addressStorageId, Boolean status, Long productId);

   @Query(value = "Select storage_id, address_storage_id From address_storage WHERE status = :status and size = :size and address_storage_id = (Select max(address_storage_id) From address_storage Where status = :status and size = :size)", nativeQuery = true)
   @Transactional
   ArrayList getStorageId(Boolean status, String size);
}
