package com.bihuniak.piotr.reactiveBePatient.domain.user;

import com.dryPepperoniStickTeam.bePatient.domain.user.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
