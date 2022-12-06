package org.xapik.crypto.users.users;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.xapik.crypto.users.users.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

  UserEntity findUserEntityByEmail(String email);

  List<UserEntity> findUserEntityByEmailIn(List<String> emails);
}
