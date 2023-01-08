package org.xapik.crypto.users.users;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserServiceTest {

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserService userService;

  @Test
  void getUsers() {
    
  }

  @Test
  void getUsersByEmails() {
  }

  @Test
  void getUser() {
  }

  @Test
  void updateUser() {
  }

  @Test
  void updateUserRole() {
  }

  @Test
  void save() {
  }

  @Test
  void deleteUser() {
  }
}