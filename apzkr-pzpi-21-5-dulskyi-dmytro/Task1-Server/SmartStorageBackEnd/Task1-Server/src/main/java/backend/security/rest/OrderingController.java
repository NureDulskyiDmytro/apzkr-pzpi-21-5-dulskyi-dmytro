package backend.security.rest;


import backend.security.model.Ordering;
import backend.security.repository.OrderingRepository;
import backend.security.service.OrderingService;
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
public class OrderingController {
   @Autowired
   private OrderingRepository orderingRepository;

   @Autowired
   private OrderingService orderingService;

   @RequestMapping(value = "/addOrdering", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   @SecurityRequirement(name = "bearerAuth")
   public ResponseEntity<Ordering> saveOrdering(@RequestBody @Valid Ordering ordering) {
      HttpHeaders headers = new HttpHeaders();

      if (ordering == null) {
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

      orderingService.save(ordering);
      return new ResponseEntity<>(ordering, headers, HttpStatus.CREATED);
   }

   @RequestMapping(value = "/updateOrdering/{orderingId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   @SecurityRequirement(name = "bearerAuth")
   public ResponseEntity<Ordering> updateOrdering(@RequestBody Ordering ordering, @PathVariable Long orderingId) {
      HttpHeaders headers = new HttpHeaders();

      if (ordering == null) {
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

      orderingService.updateOrdering(ordering, orderingId);

      return new ResponseEntity<>(ordering, headers, HttpStatus.OK);
   }

   @DeleteMapping("/deleteOrdering/{orderingId}")
   @SecurityRequirement(name = "bearerAuth")
   public ResponseEntity<Ordering> deleteOneOrdering(@PathVariable Long orderingId) {
      orderingService.deleteOneOrdering(orderingId);
      return new ResponseEntity<>(HttpStatus.OK);
   }

   @GetMapping("/getOrderingById/{orderingId}")
   @SecurityRequirement(name = "bearerAuth")
   public ResponseEntity<Optional<Ordering>> getOneOrdering(@PathVariable Long orderingId) {
      return ResponseEntity.ok(orderingService.getOneOrdering(orderingId));
   }

   @GetMapping("/getOrdering")
   @SecurityRequirement(name = "bearerAuth")
   public ResponseEntity<List<Ordering>> getAllOrderings() {
      return ResponseEntity.ok(orderingService.getAllOrderings());
   }

   @PutMapping("/updateOrdering/{orderingId}/{status}")
   @SecurityRequirement(name = "bearerAuth")
   public void updateOrdering(@PathVariable Long orderingId, @PathVariable Boolean status) {
      orderingRepository.updateOrdering(orderingId, status);
   }

   @PutMapping("/getOrderingData/{orderingId}")
   @SecurityRequirement(name = "bearerAuth")
   public ArrayList getOrderingData(@PathVariable Long orderingId) {
      return orderingRepository.getOrderingData(orderingId);
   }
}
