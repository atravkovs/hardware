package org.xapik.crypto.users.users;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xapik.crypto.users.users.model.GenericError;
import org.xapik.crypto.users.users.model.UserAlreadyExistsException;
import org.xapik.crypto.users.users.model.UserEntity;
import org.xapik.crypto.users.users.model.UserRegistrationRequest;

@CrossOrigin
@RestController
public class UsersController {

  private final UserService userService;

  @Autowired
  public UsersController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/hello")
  public String hello() {
    return "Hello 123";
  }

  @GetMapping("/users")
  public Page<UserEntity> getUsers(@RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "5") Integer pageSize) {
    return this.userService.getUsers(page, pageSize);
  }

  @GetMapping("/users/emails")
  public List<UserEntity> getUsersByEmails(@RequestParam List<String> emails) {
    return this.userService.getUsersByEmails(emails);
  }

  @PostMapping("/register")
  public ResponseEntity<?> saveUser(@Valid @RequestBody UserRegistrationRequest user) {
    try {
      return ResponseEntity.ok(userService.save(user));
    } catch (UserAlreadyExistsException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body(new GenericError(e.getLocalizedMessage()));
    }
  }

}
