package backend.security.rest;

import backend.security.model.Storage;
import backend.security.repository.StorageRepository;
import backend.security.service.StorageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StorageController {
   @Autowired
   private StorageRepository storageRepository;

   @Autowired
   private StorageService storageService;

   @RequestMapping(value = "/addStorage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   @SecurityRequirement(name = "bearerAuth")
   public ResponseEntity<Storage> saveStorage(@RequestBody @Valid Storage storage) {
      HttpHeaders headers = new HttpHeaders();

      if (storage == null) {
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

      storageService.save(storage);
      return new ResponseEntity<>(storage, headers, HttpStatus.CREATED);
   }

   @RequestMapping(value = "/updateStorage/{addressStorageId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   @SecurityRequirement(name = "bearerAuth")
   public ResponseEntity<Storage> updateStorage(@RequestBody Storage storage, @PathVariable Long addressStorageId) {
      HttpHeaders headers = new HttpHeaders();

      if (storage == null) {
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

      storageService.updateStorage(storage, addressStorageId);

      return new ResponseEntity<>(storage, headers, HttpStatus.OK);
   }

   @DeleteMapping("/deleteStorage/{addressStorageId}")
   @SecurityRequirement(name = "bearerAuth")
   public ResponseEntity<Storage> deleteOneStorage(@PathVariable Long addressStorageId) {
      storageService.deleteOneStorage(addressStorageId);
      return new ResponseEntity<>(HttpStatus.OK);
   }

   @GetMapping("/getStorageById/{addressStorageId}")
   @SecurityRequirement(name = "bearerAuth")
   public ResponseEntity<Optional<Storage>> getOneStorage(@PathVariable Long addressStorageId) {
      return ResponseEntity.ok(storageService.getOneStorage(addressStorageId));
   }

   @GetMapping("/getStorage")
   @SecurityRequirement(name = "bearerAuth")
   public ResponseEntity<List<Storage>> getAllStorages() {
      return ResponseEntity.ok(storageService.getAllStorages());
   }

   @PutMapping("/updateStorage/{addressStorageId}/{status}/{productId}")
   @SecurityRequirement(name = "bearerAuth")
   public ArrayList updateStorage(@PathVariable Long addressStorageId, @PathVariable Boolean status,
                                  @PathVariable Long productId) {
      storageRepository.updateStorage(addressStorageId, status, productId);

      return new ArrayList();
   }

   @PutMapping("/getStorageId/{size}")
   @SecurityRequirement(name = "bearerAuth")
   public ArrayList getStorageId(@PathVariable String size) {
      return storageRepository.getStorageId(false, size);
   }
}
