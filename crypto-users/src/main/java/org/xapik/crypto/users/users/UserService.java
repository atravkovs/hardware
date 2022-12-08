package org.xapik.crypto.users.users;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.xapik.crypto.users.users.model.UserAlreadyExistsException;
import org.xapik.crypto.users.users.model.UserRegistrationRequest;
import org.xapik.crypto.users.users.model.UserEntity;

@Slf4j
@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public Page<UserEntity> getUsers(int pageNumber, int pageSize) {
    var pageable = Pageable
        .ofSize(pageSize)
        .withPage(pageNumber);

    return this.userRepository.findAll(pageable);
  }

  public List<UserEntity> getUsersByEmails(List<String> emails) {
    return this.userRepository.findUserEntityByEmailIn(emails);
  }

  public UserEntity getUser(String email) {
    return userRepository.findUserEntityByEmail(email);
  }

  private String processName(String string) {
    return StringUtils.trimAllWhitespace(StringUtils.capitalize(string));
  }

  public UserEntity save(UserRegistrationRequest user) {
    UserEntity existingUser = this.getUser(user.getEmail());

    if (existingUser != null) {
      throw new UserAlreadyExistsException();
    }

    UserEntity newUser = new UserEntity();
    newUser.setName(processName(user.getName()));
    newUser.setEmail(user.getEmail());
    newUser.setSurname(processName(user.getSurname()));
    newUser.setPassword(passwordEncoder.encode(user.getPassword()));
    newUser.setRole("user");

    return userRepository.save(newUser);
  }

  public void deleteUser(String email) {
    UserEntity existingUser = this.getUser(email);

    userRepository.delete(existingUser);
  }
}
