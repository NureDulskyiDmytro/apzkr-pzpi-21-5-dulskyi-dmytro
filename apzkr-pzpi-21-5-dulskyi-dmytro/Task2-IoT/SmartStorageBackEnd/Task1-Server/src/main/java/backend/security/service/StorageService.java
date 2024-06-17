package backend.security.service;

import backend.security.model.Storage;
import backend.security.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StorageService {
   @Autowired
   private final StorageRepository storageRepository;

   public StorageService(StorageRepository storageRepository) {
      this.storageRepository = storageRepository;
   }

   public void save(Storage storage) {
      this.storageRepository.save(storage);
   }

   public Optional<Storage> getOneStorage(Long id) {
      return this.storageRepository.findById(id);
   }

   public List<Storage> getAllStorages() {
      return this.storageRepository.findAll();
   }

   public void deleteOneStorage(Long id) {
      this.storageRepository.deleteById(id);
   }

   public void updateStorage(Storage storage, Long id) {
      Storage storageToChange = this.getOneStorage(id).get();

      if (storage.getStorageId() != null) {
         storageToChange.setStorageId(storage.getStorageId());
      }

      if (storage.getSize() != null) {
         storageToChange.setSize(storage.getSize());
      }

      if (storage.getPrice() != null) {
         storageToChange.setPrice(storage.getPrice());
      }

      if (storage.getStatus() != null) {
         storageToChange.setStatus(storage.getStatus());
      }

      if (storage.getProductId() != null) {
         storageToChange.setProductId(storage.getProductId());
      }

      this.save(storageToChange);
   }
}
