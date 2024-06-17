package backend.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import backend.security.SecurityUtils;
import backend.security.model.User;
import backend.security.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional
public class UserService {

   @Autowired
   private BCryptPasswordEncoder bCryptPasswordEncoder;

   private final UserRepository userRepository;

   public UserService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Transactional(readOnly = true)
   public Optional<User> getUserWithAuthorities() {
      return SecurityUtils.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
   }

   public void save(User user) {
      userRepository.save(user);
   }

}
