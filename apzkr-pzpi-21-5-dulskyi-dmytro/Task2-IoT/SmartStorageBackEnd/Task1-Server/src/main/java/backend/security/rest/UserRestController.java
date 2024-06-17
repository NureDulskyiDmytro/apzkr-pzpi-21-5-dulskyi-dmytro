package backend.security.rest;


import backend.security.model.Authority;
import backend.security.model.User;
import backend.security.repository.UserRepository;
import backend.security.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {
   @Autowired
   private PasswordEncoder passwordEncoder;

   @Autowired
   private UserRepository userRepository;

   private final UserService userService;

   public UserRestController(UserService userService) {
      this.userService = userService;
   }

   @GetMapping("/user")
   @SecurityRequirement(name = "bearerAuth")
   public ResponseEntity<User> getActualUser() {
      return ResponseEntity.ok(userService.getUserWithAuthorities().get());
   }

   @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   @SecurityRequirement(name = "bearerAuth")
   public ResponseEntity<User> saveUser(@RequestBody @Valid User user) {
      HttpHeaders headers = new HttpHeaders();

      if (user == null) {
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

      user.setActivated(true);
      user.setAuthorities(Collections.singleton(new Authority("ROLE_USER")));
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      this.userRepository.save(user);

      return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
   }

   @GetMapping("/getUserID/{username}")
   @SecurityRequirement(name = "bearerAuth")
   public ArrayList<String> getUserID(@PathVariable String username) {
      return userRepository.getUserID(username);
   }

   @DeleteMapping("/deleteByUserID/{user_id}")
   @SecurityRequirement(name = "bearerAuth")
   public ResponseEntity<User> deleteByUserID(@PathVariable Long user_id) {
      userRepository.deleteById(user_id);
      return new ResponseEntity<User>(HttpStatus.OK);
   }

   @GetMapping("/getUserById/{user_id}")
   @SecurityRequirement(name = "bearerAuth")
   public ResponseEntity<List<User>> getUserById(@PathVariable Long user_id) {
      return ResponseEntity.ok(userRepository.findAllById(Collections.singleton(user_id)));
   }

   @GetMapping("/getUser")
   @SecurityRequirement(name = "bearerAuth")
   public ResponseEntity<List<User>> getUser() {
      return ResponseEntity.ok(userRepository.findAll());
   }

   @RequestMapping(value = "/userUpdate", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   @SecurityRequirement(name = "bearerAuth")
   public ResponseEntity<User> updateUser(@RequestBody User user) {
      HttpHeaders headers = new HttpHeaders();

      User authUser = userService.getUserWithAuthorities().get();

      User userToChange = userRepository.findByUsername(authUser.getUsername());

      if (user.getPhone() != null) {
         userToChange.setPhone(user.getPhone());
      }
      if (user.getlName() != null) {
         userToChange.setlName(user.getlName());
      }
      if (user.getfName() != null) {
         userToChange.setfName(user.getfName());
      }
      if (user.getEmail() != null) {
         userToChange.setEmail(user.getEmail());
      }
      if (user.getAge() != null) {
         userToChange.setAge(user.getAge());
      }

      this.userService.save(userToChange);

      return new ResponseEntity<>(user, headers, HttpStatus.OK);
   }

   @PutMapping("/banUser/{userId}/{activated}")
   @SecurityRequirement(name = "bearerAuth")
   public ArrayList banUser(@PathVariable Long userId, @PathVariable Boolean activated) {
      userRepository.banUser(userId, activated);
      return new ArrayList();
   }
}
